package com.injaazh.islamic.data.local.preferences

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.settingsDataStore by preferencesDataStore(name = "settings_prefs")

@Singleton
class SettingsPreferences @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val adhanEnabledKey = booleanPreferencesKey("adhan_enabled")
    private val fajrEnabledKey = booleanPreferencesKey("fajr_enabled")
    private val dhuhrEnabledKey = booleanPreferencesKey("dhuhr_enabled")
    private val asrEnabledKey = booleanPreferencesKey("asr_enabled")
    private val maghribEnabledKey = booleanPreferencesKey("maghrib_enabled")
    private val ishaEnabledKey = booleanPreferencesKey("isha_enabled")

    val adhanEnabled: Flow<Boolean> = context.settingsDataStore.data.map { it[adhanEnabledKey] ?: true }
    val fajrEnabled: Flow<Boolean> = context.settingsDataStore.data.map { it[fajrEnabledKey] ?: true }
    val dhuhrEnabled: Flow<Boolean> = context.settingsDataStore.data.map { it[dhuhrEnabledKey] ?: true }
    val asrEnabled: Flow<Boolean> = context.settingsDataStore.data.map { it[asrEnabledKey] ?: true }
    val maghribEnabled: Flow<Boolean> = context.settingsDataStore.data.map { it[maghribEnabledKey] ?: true }
    val ishaEnabled: Flow<Boolean> = context.settingsDataStore.data.map { it[ishaEnabledKey] ?: true }

    suspend fun setAdhanEnabled(enabled: Boolean) {
        context.settingsDataStore.edit { it[adhanEnabledKey] = enabled }
    }

    suspend fun setPrayerEnabled(prayerName: String, enabled: Boolean) {
        val key = when(prayerName) {
            "Fajr" -> fajrEnabledKey
            "Dhuhr" -> dhuhrEnabledKey
            "Asr" -> asrEnabledKey
            "Maghrib" -> maghribEnabledKey
            "Isha" -> ishaEnabledKey
            else -> return
        }
        context.settingsDataStore.edit { it[key] = enabled }
    }
}
