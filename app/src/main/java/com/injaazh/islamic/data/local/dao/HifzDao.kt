package com.injaazh.islamic.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.injaazh.islamic.data.local.entity.HifzEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HifzDao {
    @Query("SELECT * FROM hifz_records ORDER BY surahId ASC")
    fun getAllHifzRecords(): Flow<List<HifzEntity>>

    @Query("SELECT * FROM hifz_records WHERE surahId = :surahId")
    suspend fun getHifzRecordById(surahId: Int): HifzEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecord(record: HifzEntity)

    @Update
    suspend fun updateRecord(record: HifzEntity)

    @Query("SELECT SUM(memorizedAyahs) FROM hifz_records")
    fun getTotalMemorizedAyahs(): Flow<Int?>
}
