package com.injaazh.islamic.domain.repository

import com.injaazh.islamic.data.local.entity.DuaCategoryEntity
import com.injaazh.islamic.data.local.entity.DuaEntity
import kotlinx.coroutines.flow.Flow

interface DuaRepository {
    fun getAllCategories(): Flow<List<DuaCategoryEntity>>
    fun getDuasByCategory(categoryId: Int): Flow<List<DuaEntity>>
    fun getFavoriteDuas(): Flow<List<DuaEntity>>
    suspend fun updateDua(dua: DuaEntity)
    suspend fun populateDataIfNeeded()
}
