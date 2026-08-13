package com.injaazh.islamic.domain.repository

import com.injaazh.islamic.data.local.entity.HifzEntity
import kotlinx.coroutines.flow.Flow

interface HifzRepository {
    fun getAllRecords(): Flow<List<HifzEntity>>
    fun getTotalMemorizedCount(): Flow<Int>
    suspend fun updateProgress(record: HifzEntity)
    suspend fun populateInitialData(surahs: List<HifzEntity>)
}
