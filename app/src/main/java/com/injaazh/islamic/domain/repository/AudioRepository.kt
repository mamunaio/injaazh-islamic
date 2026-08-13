package com.injaazh.islamic.domain.repository

import androidx.media3.common.MediaItem
import kotlinx.coroutines.flow.StateFlow

interface AudioRepository {
    val currentMediaItem: StateFlow<MediaItem?>
    val isPlaying: StateFlow<Boolean>
    
    fun playAyah(url: String, title: String, artist: String)
    fun pause()
    fun resume()
    fun stop()
}
