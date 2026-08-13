package com.injaazh.islamic.domain.repository

import com.injaazh.islamic.domain.model.PrayerTimesState
import kotlinx.coroutines.flow.Flow

interface PrayerRepository {
    fun getPrayerTimes(): Flow<PrayerTimesState>
}
