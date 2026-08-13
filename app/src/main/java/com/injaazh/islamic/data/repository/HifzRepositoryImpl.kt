package com.injaazh.islamic.data.repository

import com.injaazh.islamic.data.local.dao.HifzDao
import com.injaazh.islamic.data.local.entity.HifzEntity
import com.injaazh.islamic.domain.repository.HifzRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HifzRepositoryImpl @Inject constructor(
    private val dao: HifzDao
) : HifzRepository {
    override fun getAllRecords(): Flow<List<HifzEntity>> = dao.getAllHifzRecords()

    override fun getTotalMemorizedCount(): Flow<Int> = dao.getTotalMemorizedAyahs().map { it ?: 0 }

    override suspend fun updateProgress(record: HifzEntity) {
        dao.updateRecord(record)
    }

    override suspend fun populateInitialData(surahs: List<HifzEntity>) {
        val existing = dao.getAllHifzRecords().first()
        if (existing.isEmpty()) {
            surahs.forEach { dao.insertRecord(it) }
        }
    }
}
