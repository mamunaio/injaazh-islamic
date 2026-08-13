package com.injaazh.islamic.di

import com.google.gson.Gson
import com.injaazh.islamic.data.remote.api.QuranApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()

    @Provides
    @Singleton
    fun provideQuranApi(gson: Gson): QuranApi {
        return Retrofit.Builder()
            .baseUrl(QuranApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(QuranApi::class.java)
    }
}
