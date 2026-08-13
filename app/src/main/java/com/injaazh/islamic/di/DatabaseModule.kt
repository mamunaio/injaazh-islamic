package com.injaazh.islamic.di

import android.content.Context
import androidx.room.Room
import com.injaazh.islamic.data.local.InjaazhDatabase
import com.injaazh.islamic.data.local.dao.DuaDao
import com.injaazh.islamic.data.local.dao.HifzDao
import com.injaazh.islamic.data.local.dao.PrayerTrackerDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): InjaazhDatabase {
        return Room.databaseBuilder(
            context,
            InjaazhDatabase::class.java,
            InjaazhDatabase.DATABASE_NAME
        )
        .fallbackToDestructiveMigration()
        .build()
    }

    @Provides
    @Singleton
    fun providePrayerTrackerDao(db: InjaazhDatabase): PrayerTrackerDao {
        return db.prayerTrackerDao
    }

    @Provides
    @Singleton
    fun provideDuaDao(db: InjaazhDatabase): DuaDao {
        return db.duaDao
    }

    @Provides
    @Singleton
    fun provideHifzDao(db: InjaazhDatabase): HifzDao {
        return db.hifzDao
    }
}
