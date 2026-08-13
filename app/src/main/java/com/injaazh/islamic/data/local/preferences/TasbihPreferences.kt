package com.injaazh.islamic.data.local.preferences

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.tasbihDataStore by preferencesDataStore(name = "tasbih_prefs")

@Singleton
class TasbihPreferences @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val countKey = intPreferencesKey("tasbih_count")
    private val targetKey = intPreferencesKey("tasbih_target")

    val count: Flow<Int> = context.tasbihDataStore.data.map { it[countKey] ?: 0 }
    val target: Flow<Int> = context.tasbihDataStore.data.map { it[targetKey] ?: 33 }

    suspend fun saveCount(count: Int) {
        context.tasbihDataStore.edit { it[countKey] = count }
    }

    suspend fun saveTarget(target: Int) {
        context.tasbihDataStore.edit { it[targetKey] = target }
    }
}
