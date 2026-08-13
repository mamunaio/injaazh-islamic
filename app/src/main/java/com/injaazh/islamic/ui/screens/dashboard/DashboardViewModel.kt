package com.injaazh.islamic.ui.screens.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.injaazh.islamic.data.local.entity.PrayerTrackerEntity
import com.injaazh.islamic.data.manager.PrayerScheduler
import com.injaazh.islamic.domain.model.PrayerTimesState
import com.injaazh.islamic.domain.repository.PrayerRepository
import com.injaazh.islamic.domain.repository.TrackerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val prayerRepository: PrayerRepository,
    private val prayerScheduler: PrayerScheduler,
    private val trackerRepository: TrackerRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PrayerTimesState())
    val uiState: StateFlow<PrayerTimesState> = _uiState.asStateFlow()

    private val _currentTime = MutableStateFlow(System.currentTimeMillis())
    val currentTime: StateFlow<Long> = _currentTime.asStateFlow()

    private val _todayTracker = MutableStateFlow<PrayerTrackerEntity?>(null)
    val todayTracker: StateFlow<PrayerTrackerEntity?> = _todayTracker

    init {
        loadPrayerTimes()
        startClock()
        loadTodayTracker()
    }

    private fun startClock() {
        viewModelScope.launch {
            while (true) {
                _currentTime.value = System.currentTimeMillis()
                delay(1000)
            }
        }
    }

    private fun loadTodayTracker() {
        val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        viewModelScope.launch {
            trackerRepository.getTrackerByDate(today).let {
                _todayTracker.value = it ?: PrayerTrackerEntity(today)
            }
        }
    }

    fun togglePrayer(name: String) {
        viewModelScope.launch {
            val current = _todayTracker.value ?: return@launch
            val updated = when(name) {
                "Fajr" -> current.copy(fajr = !current.fajr)
                "Dhuhr" -> current.copy(dhuhr = !current.dhuhr)
                "Asr" -> current.copy(asr = !current.asr)
                "Maghrib" -> current.copy(maghrib = !current.maghrib)
                "Isha" -> current.copy(isha = !current.isha)
                else -> current
            }
            trackerRepository.updateTracker(updated)
            _todayTracker.value = updated
        }
    }

    fun refreshLocation() {
        loadPrayerTimes()
    }

    private fun loadPrayerTimes() {
        viewModelScope.launch {
            prayerRepository.getPrayerTimes().collect {
                _uiState.value = it
                prayerScheduler.scheduleNextAdhan()
            }
        }
    }
}
