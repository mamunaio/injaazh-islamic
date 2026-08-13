package com.injaazh.islamic.domain.repository

import com.injaazh.islamic.data.remote.model.ChapterDto
import com.injaazh.islamic.data.remote.model.VerseDto
import kotlinx.coroutines.flow.Flow

interface QuranRepository {
    fun getChapters(): Flow<List<ChapterDto>>
    fun getVersesByChapter(chapterId: Int): Flow<List<VerseDto>>
}
