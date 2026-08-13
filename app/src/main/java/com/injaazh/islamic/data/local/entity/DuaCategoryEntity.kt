package com.injaazh.islamic.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dua_categories")
data class DuaCategoryEntity(
    @PrimaryKey val id: Int,
    val nameEn: String,
    val nameBn: String,
    val iconName: String? = null
)
