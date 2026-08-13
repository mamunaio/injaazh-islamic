package com.injaazh.islamic.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "hifz_records")
data class HifzEntity(
    @PrimaryKey val surahId: Int,
    val surahNameBn: String,
    val surahNameAr: String,
    val totalAyahs: Int,
    val memorizedAyahs: Int = 0,
    val isCompleted: Boolean = false,
    val lastUpdated: Long = System.currentTimeMillis()
)
