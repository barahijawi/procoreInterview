package com.example.procoreinterview.data.repository

import com.example.procoreinterview.data.api.PockemonApiService
import com.example.procoreinterview.data.database.MinMaxHp
import com.example.procoreinterview.data.database.PockemonCardDao
import com.example.procoreinterview.data.database.PockemonCardEntity
import com.example.procoreinterview.domain.PockemonCard
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PockemonRepositoryImpl @Inject constructor(
    private val apiService: PockemonApiService,
    private val pokemonCardDao: PockemonCardDao
) : PockemonRepository {

    // Fetch data from API and map to domain model
    override suspend fun getPockemonCards(): List<PockemonCard> {
        val apiResponse = apiService.getPockemonCards()
        return apiResponse.data.map { apiCard ->
            PockemonCard(
                id = apiCard.id,
                name = apiCard.name,
                hp = apiCard.hp,
                imageUrlSmall = apiCard.images.small,
                imageUrlLarge = apiCard.images.large,
                superType = apiCard.superType,
                types = apiCard.types,         // Directly map from the API
                subTypes = apiCard.subtypes    // Directly map from the API
            )
        }
    }

    // Fetch cached data from RoomDB and map to domain model
    override fun getCachedPokemonCards(): Flow<List<PockemonCard>> {
        return pokemonCardDao.getAllPockemonCards().map { entities ->
            entities.map { entity ->
                PockemonCard(
                    id = entity.id,
                    name = entity.name,
                    hp = entity.hp,
                    imageUrlSmall = entity.imageUrl,
                    imageUrlLarge = entity.imageUrlLarge,
                    superType = entity.superType,
                    types = entity.types.split(","),      // Convert comma-separated string to list
                    subTypes = entity.subTypes.split(",") // Convert comma-separated string to list
                )
            }
        }
    }

    // Save data to RoomDB
    override suspend fun savePokemonCards(cards: List<PockemonCard>) {
        val entities = cards.map { card ->
            PockemonCardEntity(
                id = card.id,
                name = card.name,
                hp = card.hp,
                imageUrl = card.imageUrlSmall,
                imageUrlLarge = card.imageUrlLarge,
                superType = card.superType,
                types = card.types.joinToString(","),  // Convert list to comma-separated string
                subTypes = card.subTypes.joinToString(",") // Convert list to comma-separated string
            )
        }
        pokemonCardDao.insertAll(entities)
    }


    override suspend fun getMinMaxHP(): MinMaxHp
    {
        return pokemonCardDao.getMinMaxHP()
    }
}
