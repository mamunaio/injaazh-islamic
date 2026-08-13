package com.injaazh.islamic.ui.screens.prayer

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.injaazh.islamic.domain.model.PrayerRow
import com.injaazh.islamic.domain.model.PrayerRowType
import com.injaazh.islamic.domain.repository.PrayerRepository
import com.injaazh.islamic.util.formatToTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class PrayerTimesViewModel @Inject constructor(
    private val prayerRepository: PrayerRepository
) : ViewModel() {

    private val _prayerRows = MutableStateFlow<List<PrayerRow>>(emptyList())
    val prayerRows: StateFlow<List<PrayerRow>> = _prayerRows.asStateFlow()

    private val _currentTime = MutableStateFlow(System.currentTimeMillis())
    val currentTime: StateFlow<Long> = _currentTime.asStateFlow()

    private val _selectedDate = MutableStateFlow(Date())
    val selectedDate: StateFlow<Date> = _selectedDate.asStateFlow()

    init {
        loadData()
        startClock()
    }

    private fun startClock() {
        viewModelScope.launch {
            while (true) {
                _currentTime.value = System.currentTimeMillis()
                delay(1000)
            }
        }
    }

    fun changeDate(days: Int) {
        val calendar = Calendar.getInstance()
        calendar.time = _selectedDate.value
        calendar.add(Calendar.DAY_OF_YEAR, days)
        _selectedDate.value = calendar.time
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            val state = prayerRepository.getPrayerTimes().first()
            val rows = mutableListOf<PrayerRow>()

            // 1. Tahajjud (Optional)
            rows.add(PrayerRow("tahajjud", "তাহাজ্জুদ", "শেষ রাত", Icons.Default.NightsStay, PrayerRowType.STANDARD))
            
            // 2. Fajr
            state.fajr?.let { rows.add(PrayerRow("fajr", "ফজর", "${it.time.formatToTime()} - সূর্যোদয়", Icons.Default.WbTwilight, PrayerRowType.STANDARD)) }
            
            // 3. Sunrise (Event)
            state.sunrise?.let { 
                rows.add(PrayerRow("sunrise", "সূর্যোদয়", it.time.formatToTime(), Icons.Default.WbSunny, PrayerRowType.EVENT))
                // 4. Forbidden (Post Sunrise)
                rows.add(PrayerRow("forbidden_1", "নিষিদ্ধ সময় (সূর্যোদয়)", "${it.time.formatToTime()} - ২০ মিনিট পর", Icons.Default.Warning, PrayerRowType.FORBIDDEN))
            }

            // 5. Salatul Duha (Optional)
            rows.add(PrayerRow("duha", "সালাতুদ দুহা", "চাশত", Icons.Default.WbSunny, PrayerRowType.STANDARD))

            // 6. Forbidden (Pre Dhuhr)
            state.dhuhr?.let {
                rows.add(PrayerRow("forbidden_2", "নিষিদ্ধ সময় (যোহর পূর্ব)", "দুপুর ১২:০০ - ${it.time.formatToTime()}", Icons.Default.Warning, PrayerRowType.FORBIDDEN))
                // 7. Dhuhr
                rows.add(PrayerRow("dhuhr", "যোহর", "${it.time.formatToTime()} - আসর", Icons.Default.WbSunny, PrayerRowType.STANDARD))
            }

            // 8. Asr
            state.asr?.let { rows.add(PrayerRow("asr", "আসর", "${it.time.formatToTime()} - মাগরিব", Icons.Default.WbCloudy, PrayerRowType.STANDARD, "Hanafi")) }

            // 9. Forbidden (Pre Sunset)
            state.maghrib?.let {
                rows.add(PrayerRow("forbidden_3", "নিষিদ্ধ সময় (সূর্যাস্ত)", "মাগরিবের ২০ মিনিট আগে", Icons.Default.Warning, PrayerRowType.FORBIDDEN))
                // 10. Maghrib
                rows.add(PrayerRow("maghrib", "মাগরিব", "${it.time.formatToTime()} - এশা", Icons.Default.WbTwilight, PrayerRowType.STANDARD))
            }

            // 11. Isha
            state.isha?.let { rows.add(PrayerRow("isha", "এশা", "${it.time.formatToTime()} - মধ্যরাত", Icons.Default.NightsStay, PrayerRowType.STANDARD)) }

            _prayerRows.value = rows
        }
    }
}
