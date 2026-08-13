package com.injaazh.islamic.data.repository

import com.injaazh.islamic.data.local.dao.PrayerTrackerDao
import com.injaazh.islamic.data.local.entity.PrayerTrackerEntity
import com.injaazh.islamic.domain.repository.TrackerRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TrackerRepositoryImpl @Inject constructor(
    private val dao: PrayerTrackerDao
) : TrackerRepository {
    override suspend fun getTrackerByDate(date: String): PrayerTrackerEntity? {
        return dao.getTrackerByDate(date)
    }

    override fun getRecentTrackers(limit: Int): Flow<List<PrayerTrackerEntity>> {
        return dao.getRecentTrackers(limit)
    }

    override suspend fun updateTracker(tracker: PrayerTrackerEntity) {
        dao.insertOrUpdate(tracker)
    }

    override fun getAllTrackers(): Flow<List<PrayerTrackerEntity>> {
        return dao.getAllTrackers()
    }
}
