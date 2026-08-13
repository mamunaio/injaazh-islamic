package com.injaazh.islamic.data.remote.api

import com.injaazh.islamic.data.remote.model.ChapterResponse
import com.injaazh.islamic.data.remote.model.VerseResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface QuranApi {

    @GET("chapters")
    suspend fun getChapters(
        @Query("language") language: String = "bn"
    ): ChapterResponse

    @GET("verses/by_chapter/{chapterId}")
    suspend fun getVersesByChapter(
        @Path("chapterId") chapterId: Int,
        @Query("language") language: String = "bn",
        @Query("words") words: Boolean = false,
        @Query("translations") translations: String = "161", // Bayan Foundation (Bengali)
        @Query("fields") fields: String = "text_uthmani",
        @Query("audio") audio: Int = 7 // Mishary Rashid Alafasy
    ): VerseResponse

    companion object {
        const val BASE_URL = "https://api.quran.com/api/v4/"
    }
}
