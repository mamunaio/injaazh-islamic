package com.injaazh.islamic.ui.screens.tasbih

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.injaazh.islamic.data.local.preferences.TasbihPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasbihViewModel @Inject constructor(
    private val preferences: TasbihPreferences
) : ViewModel() {

    val count: StateFlow<Int> = preferences.count
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val target: StateFlow<Int> = preferences.target
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 33)

    fun incrementCount() {
        viewModelScope.launch {
            preferences.saveCount(count.value + 1)
        }
    }

    fun resetCount() {
        viewModelScope.launch {
            preferences.saveCount(0)
        }
    }

    fun setTarget(newTarget: Int) {
        viewModelScope.launch {
            preferences.saveTarget(newTarget)
        }
    }
}
