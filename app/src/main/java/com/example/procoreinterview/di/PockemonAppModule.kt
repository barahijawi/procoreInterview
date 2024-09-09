package com.example.procoreinterview.di

import com.example.procoreinterview.data.api.PockemonApiService
import com.example.procoreinterview.data.repository.PockemonRepository
import com.example.procoreinterview.data.repository.PockemonRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PockemonAppModule
{
    @Provides
    @Singleton
    fun providePockemonApiService(): PockemonApiService{
        return  Retrofit.Builder()
            .baseUrl("https://api.pokemontcg.io/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PockemonApiService::class.java)
    }

    @Provides
    @Singleton
    fun providePockemonRepository(apiService: PockemonApiService):PockemonRepository{
        return PockemonRepositoryImpl(apiService)
    }

}