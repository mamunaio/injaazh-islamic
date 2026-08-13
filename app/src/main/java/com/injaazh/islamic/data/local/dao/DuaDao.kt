package com.injaazh.islamic.data.local.dao

import androidx.room.*
import com.injaazh.islamic.data.local.entity.DuaCategoryEntity
import com.injaazh.islamic.data.local.entity.DuaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DuaDao {
    @Query("SELECT * FROM dua_categories")
    fun getAllCategories(): Flow<List<DuaCategoryEntity>>

    @Query("SELECT * FROM duas WHERE categoryId = :categoryId")
    fun getDuasByCategory(categoryId: Int): Flow<List<DuaEntity>>

    @Query("SELECT * FROM duas WHERE isFavorite = 1")
    fun getFavoriteDuas(): Flow<List<DuaEntity>>

    @Update
    suspend fun updateDua(dua: DuaEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategories(categories: List<DuaCategoryEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDuas(duas: List<DuaEntity>)
}
