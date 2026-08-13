package com.injaazh.islamic.ui.screens.quran

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.injaazh.islamic.data.local.entity.HifzEntity
import com.injaazh.islamic.domain.repository.HifzRepository
import com.injaazh.islamic.domain.repository.QuranRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HifzViewModel @Inject constructor(
    private val hifzRepository: HifzRepository,
    private val quranRepository: QuranRepository
) : ViewModel() {

    val hifzRecords: StateFlow<List<HifzEntity>> = hifzRepository.getAllRecords()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val totalMemorized: StateFlow<Int> = hifzRepository.getTotalMemorizedCount()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    init {
        initializeHifzData()
    }

    private fun initializeHifzData() {
        viewModelScope.launch {
            val records = hifzRepository.getAllRecords().first()
            if (records.isEmpty()) {
                quranRepository.getChapters().collect { chapters ->
                    if (chapters.isNotEmpty()) {
                        val entities = chapters.map {
                            HifzEntity(
                                surahId = it.id,
                                surahNameBn = it.translatedName.name,
                                surahNameAr = it.nameArabic,
                                totalAyahs = it.versesCount
                            )
                        }
                        hifzRepository.populateInitialData(entities)
                    }
                }
            }
        }
    }

    fun updateProgress(surahId: Int, memorizedCount: Int) {
        viewModelScope.launch {
            val current = hifzRecords.value.find { it.surahId == surahId }
            current?.let {
                val updated = it.copy(
                    memorizedAyahs = memorizedCount,
                    isCompleted = memorizedCount >= it.totalAyahs,
                    lastUpdated = System.currentTimeMillis()
                )
                hifzRepository.updateProgress(updated)
            }
        }
    }
}
