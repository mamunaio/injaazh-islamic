package com.injaazh.islamic.ui.screens.settings

import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.injaazh.islamic.data.local.preferences.SettingsPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val preferences: SettingsPreferences
) : ViewModel() {

    val adhanEnabled = preferences.adhanEnabled
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), true)

    val currentLanguage: String
        get() = AppCompatDelegate.getApplicationLocales()[0]?.language ?: "en"

    fun setLanguage(languageCode: String) {
        val appLocale: LocaleListCompat = LocaleListCompat.forLanguageTags(languageCode)
        AppCompatDelegate.setApplicationLocales(appLocale)
    }

    fun toggleAdhan(enabled: Boolean) {
        viewModelScope.launch {
            preferences.setAdhanEnabled(enabled)
        }
    }
}
