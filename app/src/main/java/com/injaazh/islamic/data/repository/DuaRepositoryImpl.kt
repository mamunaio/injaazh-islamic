package com.injaazh.islamic.data.repository

import android.content.Context
import com.google.gson.Gson
import com.injaazh.islamic.data.local.dao.DuaDao
import com.injaazh.islamic.data.local.entity.DuaCategoryEntity
import com.injaazh.islamic.data.local.entity.DuaEntity
import com.injaazh.islamic.domain.repository.DuaRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class DuaRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val dao: DuaDao,
    private val gson: Gson
) : DuaRepository {

    override fun getAllCategories(): Flow<List<DuaCategoryEntity>> = dao.getAllCategories()

    override fun getDuasByCategory(categoryId: Int): Flow<List<DuaEntity>> = dao.getDuasByCategory(categoryId)

    override fun getFavoriteDuas(): Flow<List<DuaEntity>> = dao.getFavoriteDuas()

    override suspend fun updateDua(dua: DuaEntity) {
        dao.updateDua(dua)
    }

    override suspend fun populateDataIfNeeded() {
        val existing = dao.getAllCategories().first()
        if (existing.isEmpty()) {
            val jsonString = context.assets.open("duas.json").bufferedReader().use { it.readText() }
            val data = gson.fromJson(jsonString, DuaData::class.java)
            dao.insertCategories(data.categories)
            dao.insertDuas(data.duas)
        }
    }

    private data class DuaData(
        val categories: List<DuaCategoryEntity>,
        val duas: List<DuaEntity>
    )
}
