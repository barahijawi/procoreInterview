package com.example.procoreinterview.data.repository

import android.util.Log
import com.example.procoreinterview.data.api.PockemonApiService
import com.example.procoreinterview.data.database.PockemonCardDao
import com.example.procoreinterview.data.database.PockemonCardEntity
import com.example.procoreinterview.domain.PockemonCard
import javax.inject.Inject

class PockemonRepositoryImpl @Inject constructor(
    private val apiService: PockemonApiService,
    private val pockemonCardDao: PockemonCardDao
) : PockemonRepository {

    override suspend fun getPockemonCards(page: Int, pageSize: Int): List<PockemonCard> {
        // First, try to load from the local database
        val cachedCards = pockemonCardDao.getAll(page, pageSize)

        Log.d("Repository", "Loaded ${cachedCards.size} cards from database")

        cachedCards.let { entities ->
            if (entities.isNotEmpty()) {
                // If we have cached cards, map them to domain models and return them
                return entities.map { entity ->
                    PockemonCard(
                        id = entity.id,
                        name = entity.name,
                        hp = entity.hp,
                        imageUrl = entity.imageUrl
                    )
                }
            }
        }

        // If no cached data, make an API call
        return try {
            Log.d("Repository", "No cached data, making API call...")
            val response = apiService.getPockemonCards()
            val cards = response.data.map { apiCard ->
                PockemonCard(
                    id = apiCard.id,
                    name = apiCard.name,
                    hp = apiCard.hp,
                    imageUrl = apiCard.images.small
                )
            }

            // Save the cards to the local database for future use
            saveCardsToDatabase(cards)
            Log.d("Repository", "Saved ${cards.size} cards to the database")

            // Return the cards from the API
            cards
        } catch (e: Exception) {
            Log.e("Repository", "Failed to fetch Pokemon cards from API", e)
            emptyList() // Return an empty list if both the API and database fail
        }
    }

    // Helper function to save API cards to the local database
    private suspend fun saveCardsToDatabase(cards: List<PockemonCard>) {
        val cardEntities = cards.map { card ->
            PockemonCardEntity(
                id = card.id,
                name = card.name,
                hp = card.hp,
                imageUrl = card.imageUrl
            )
        }
        pockemonCardDao.insertAll(cardEntities)
    }

    override suspend fun getCardCount(): Int {
        return pockemonCardDao.getCount()
    }

}
