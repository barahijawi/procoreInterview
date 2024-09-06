package com.example.procoreinterview.di


import android.content.Context
import androidx.room.Room
import com.example.procoreinterview.data.api.ApiService
import com.example.procoreinterview.data.db.AppDatabase
import com.example.procoreinterview.data.db.ItemDao
import com.example.procoreinterview.data.db.ItemDetailsDao
import com.example.procoreinterview.util.Constants
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule
{

    val gson = GsonBuilder().setLenient().create()

    @Provides
    @Singleton
    fun providesApiService(): ApiService
    {
        return Retrofit.Builder()
            .baseUrl(
                Constants.BASE_URL
            )

            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase
    {
        appContext.deleteDatabase("items_db")

        return Room.databaseBuilder(appContext, AppDatabase::class.java, "items_db").build()
    }

    @Provides
    fun provideItemDao(database: AppDatabase): ItemDao = database.itemDao()

    @Provides
    fun provideItemDetailsDao(database: AppDatabase) : ItemDetailsDao =  database.itemDetailsDao()

}