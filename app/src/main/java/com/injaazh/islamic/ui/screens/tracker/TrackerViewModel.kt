package com.injaazh.islamic.ui.screens.tracker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.injaazh.islamic.data.local.entity.PrayerTrackerEntity
import com.injaazh.islamic.domain.repository.TrackerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class TrackerViewModel @Inject constructor(
    private val repository: TrackerRepository
) : ViewModel() {

    private val todayDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

    private val _todayTracker = MutableStateFlow<PrayerTrackerEntity?>(null)
    val todayTracker: StateFlow<PrayerTrackerEntity?> = _todayTracker

    val allTrackers = repository.getAllTrackers()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val overallProgress = allTrackers.map { list ->
        if (list.isEmpty()) 0f
        else {
            val totalPrayers = list.size * 5
            val prayersDone = list.sumOf { 
                (if (it.fajr) 1 else 0) + (if (it.dhuhr) 1 else 0) + (if (it.asr) 1 else 0) + (if (it.maghrib) 1 else 0) + (if (it.isha) 1 else 0)
            }
            prayersDone.toFloat() / totalPrayers
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0f)

    init {
        loadTodayTracker()
    }

    private fun loadTodayTracker() {
        viewModelScope.launch {
            val tracker = repository.getTrackerByDate(todayDate) ?: PrayerTrackerEntity(todayDate)
            _todayTracker.value = tracker
        }
    }

    fun togglePrayer(prayerName: String) {
        viewModelScope.launch {
            val current = _todayTracker.value ?: PrayerTrackerEntity(todayDate)
            val updated = when (prayerName) {
                "Fajr" -> current.copy(fajr = !current.fajr)
                "Dhuhr" -> current.copy(dhuhr = !current.dhuhr)
                "Asr" -> current.copy(asr = !current.asr)
                "Maghrib" -> current.copy(maghrib = !current.maghrib)
                "Isha" -> current.copy(isha = !current.isha)
                else -> current
            }
            repository.updateTracker(updated)
            _todayTracker.value = updated
        }
    }
}
