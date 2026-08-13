package com.injaazh.islamic.ui.screens.quran

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.injaazh.islamic.data.remote.model.ChapterDto
import com.injaazh.islamic.data.remote.model.VerseDto
import com.injaazh.islamic.domain.repository.QuranRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuranViewModel @Inject constructor(
    private val repository: QuranRepository
) : ViewModel() {

    private val _chapters = MutableStateFlow<List<ChapterDto>>(emptyList())
    val chapters: StateFlow<List<ChapterDto>> = _chapters.asStateFlow()

    private val _verses = MutableStateFlow<List<VerseDto>>(emptyList())
    val verses: StateFlow<List<VerseDto>> = _verses.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        loadChapters()
    }

    private fun loadChapters() {
        viewModelScope.launch {
            _isLoading.value = true
            repository.getChapters().collect {
                _chapters.value = it
                _isLoading.value = false
            }
        }
    }

    fun loadVerses(chapterId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            _verses.value = emptyList() // Clear previous verses
            repository.getVersesByChapter(chapterId).collect {
                _verses.value = it
                _isLoading.value = false
            }
        }
    }
}
