package com.injaazh.islamic.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "duas",
    foreignKeys = [
        ForeignKey(
            entity = DuaCategoryEntity::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class DuaEntity(
    @PrimaryKey val id: Int,
    val categoryId: Int,
    val arabicText: String,
    val transliterationBn: String? = null,
    val translationBn: String? = null,
    val translationEn: String? = null,
    val reference: String? = null,
    val repeatCount: Int = 1,
    val isFavorite: Boolean = false
)
