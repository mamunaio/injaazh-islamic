package com.injaazh.islamic.data.repository

import com.injaazh.islamic.data.remote.api.QuranApi
import com.injaazh.islamic.data.remote.model.ChapterDto
import com.injaazh.islamic.data.remote.model.VerseDto
import com.injaazh.islamic.domain.repository.QuranRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class QuranRepositoryImpl @Inject constructor(
    private val api: QuranApi
) : QuranRepository {

    override fun getChapters(): Flow<List<ChapterDto>> = flow {
        try {
            val response = api.getChapters()
            emit(response.chapters)
        } catch (e: Exception) {
            emit(emptyList())
        }
    }

    override fun getVersesByChapter(chapterId: Int): Flow<List<VerseDto>> = flow {
        try {
            val response = api.getVersesByChapter(chapterId)
            emit(response.verses)
        } catch (e: Exception) {
            emit(emptyList())
        }
    }
}
