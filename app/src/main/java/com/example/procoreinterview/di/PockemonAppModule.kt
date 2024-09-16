package com.example.procoreinterview.di

import android.content.Context
import android.util.Log
import com.example.procoreinterview.data.api.PockemonApiService
import com.example.procoreinterview.data.database.PockemonCardDao
import com.example.procoreinterview.data.database.PockemonDatabase
import com.example.procoreinterview.data.repository.PockemonRepository
import com.example.procoreinterview.data.repository.PockemonRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PockemonAppModule
{
    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor
    {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY // You can change the level to HEADERS if needed
        return logging
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        retryInterceptor: RetryInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor) // Logging Interceptor
            .addInterceptor(retryInterceptor)   // Retry Interceptor
            .build()
    }

    @Provides
    @Singleton
    fun provideRetryInterceptor(): RetryInterceptor {

        return RetryInterceptor(maxRetries = 3) // You can configure the number of retries
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.pokemontcg.io/v2/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    @Provides
    @Singleton
    fun providePockemonApiService(retrofit: Retrofit): PockemonApiService {
        return retrofit.create(PockemonApiService::class.java)
    }

    @Provides
    @Singleton
    fun providePockemonRepository(
        apiService: PockemonApiService,
        pockemonCardDao: PockemonCardDao // Add DAO here
    ): PockemonRepository {
        return PockemonRepositoryImpl(apiService, pockemonCardDao)
    }

    @Provides
    @Singleton
    fun providePokemonDatabase(@ApplicationContext context: Context): PockemonDatabase
    {
        return PockemonDatabase.getDatabase(context)
    }

    @Provides
    fun providePokemonCardDao(database: PockemonDatabase): PockemonCardDao
    {
        return database.pockemonCardDao()
    }
}

class RetryInterceptor(private val maxRetries: Int) : Interceptor {
    private var retryCount = 0

    override fun intercept(chain: Interceptor.Chain): Response {
        var response = chain.proceed(chain.request())

        // Retry logic
        while (!response.isSuccessful && retryCount < maxRetries) {
            retryCount++
            Log.d("RetryInterceptor", "Request failed - Retry attempt $retryCount")

            // Important: close the previous response before retrying
            response.close()

            // Retry the request
            response = chain.proceed(chain.request())
        }

        return response
    }
}
