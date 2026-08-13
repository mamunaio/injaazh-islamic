package com.injaazh.islamic.ui.screens.quran

import androidx.lifecycle.ViewModel
import com.injaazh.islamic.domain.repository.AudioRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class AudioViewModel @Inject constructor(
    private val repository: AudioRepository
) : ViewModel() {

    val currentMediaItem = repository.currentMediaItem
    val isPlaying = repository.isPlaying

    fun playAyah(url: String, title: String, artist: String) {
        repository.playAyah(url, title, artist)
    }

    fun pause() {
        repository.pause()
    }

    fun resume() {
        repository.resume()
    }

    fun stop() {
        repository.stop()
    }
}
