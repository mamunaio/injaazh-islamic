package com.injaazh.islamic.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.injaazh.islamic.data.local.entity.PrayerTrackerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PrayerTrackerDao {
    @Query("SELECT * FROM prayer_tracker WHERE date = :date")
    suspend fun getTrackerByDate(date: String): PrayerTrackerEntity?

    @Query("SELECT * FROM prayer_tracker ORDER BY date DESC LIMIT :limit")
    fun getRecentTrackers(limit: Int): Flow<List<PrayerTrackerEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(tracker: PrayerTrackerEntity)

    @Query("SELECT * FROM prayer_tracker")
    fun getAllTrackers(): Flow<List<PrayerTrackerEntity>>
}
