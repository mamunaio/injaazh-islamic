package com.injaazh.islamic.di

import com.injaazh.islamic.data.repository.AudioRepositoryImpl
import com.injaazh.islamic.data.repository.DuaRepositoryImpl
import com.injaazh.islamic.data.repository.HifzRepositoryImpl
import com.injaazh.islamic.data.repository.PrayerRepositoryImpl
import com.injaazh.islamic.data.repository.QuranRepositoryImpl
import com.injaazh.islamic.data.repository.TrackerRepositoryImpl
import com.injaazh.islamic.domain.repository.AudioRepository
import com.injaazh.islamic.domain.repository.DuaRepository
import com.injaazh.islamic.domain.repository.HifzRepository
import com.injaazh.islamic.domain.repository.PrayerRepository
import com.injaazh.islamic.domain.repository.QuranRepository
import com.injaazh.islamic.domain.repository.TrackerRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindPrayerRepository(
        prayerRepositoryImpl: PrayerRepositoryImpl
    ): PrayerRepository

    @Binds
    @Singleton
    abstract fun bindTrackerRepository(
        trackerRepositoryImpl: TrackerRepositoryImpl
    ): TrackerRepository

    @Binds
    @Singleton
    abstract fun bindQuranRepository(
        quranRepositoryImpl: QuranRepositoryImpl
    ): QuranRepository

    @Binds
    @Singleton
    abstract fun bindAudioRepository(
        audioRepositoryImpl: AudioRepositoryImpl
    ): AudioRepository

    @Binds
    @Singleton
    abstract fun bindDuaRepository(
        duaRepositoryImpl: DuaRepositoryImpl
    ): DuaRepository

    @Binds
    @Singleton
    abstract fun bindHifzRepository(
        hifzRepositoryImpl: HifzRepositoryImpl
    ): HifzRepository
}
