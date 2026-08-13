package com.injaazh.islamic.domain.repository

import com.injaazh.islamic.data.local.entity.PrayerTrackerEntity
import kotlinx.coroutines.flow.Flow

interface TrackerRepository {
    suspend fun getTrackerByDate(date: String): PrayerTrackerEntity?
    fun getRecentTrackers(limit: Int): Flow<List<PrayerTrackerEntity>>
    suspend fun updateTracker(tracker: PrayerTrackerEntity)
    fun getAllTrackers(): Flow<List<PrayerTrackerEntity>>
}
