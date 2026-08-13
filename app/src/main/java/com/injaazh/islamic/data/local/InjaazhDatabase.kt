package com.injaazh.islamic.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.injaazh.islamic.data.local.dao.DuaDao
import com.injaazh.islamic.data.local.dao.HifzDao
import com.injaazh.islamic.data.local.dao.PrayerTrackerDao
import com.injaazh.islamic.data.local.entity.DuaCategoryEntity
import com.injaazh.islamic.data.local.entity.DuaEntity
import com.injaazh.islamic.data.local.entity.HifzEntity
import com.injaazh.islamic.data.local.entity.PrayerTrackerEntity

@Database(
    entities = [
        PrayerTrackerEntity::class,
        DuaCategoryEntity::class,
        DuaEntity::class,
        HifzEntity::class
    ],
    version = 3,
    exportSchema = false
)
abstract class InjaazhDatabase : RoomDatabase() {
    abstract val prayerTrackerDao: PrayerTrackerDao
    abstract val duaDao: DuaDao
    abstract val hifzDao: HifzDao

    companion object {
        const val DATABASE_NAME = "injaazh_db"
    }
}
