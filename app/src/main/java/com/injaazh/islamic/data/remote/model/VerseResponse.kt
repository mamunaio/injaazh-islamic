package com.injaazh.islamic.data.remote.model

import com.google.gson.annotations.SerializedName

data class VerseResponse(
    @SerializedName("verses") val verses: List<VerseDto>
)

data class VerseDto(
    @SerializedName("id") val id: Int,
    @SerializedName("verse_number") val verseNumber: Int,
    @SerializedName("verse_key") val verseKey: String,
    @SerializedName("text_uthmani") val textUthmani: String,
    @SerializedName("translations") val translations: List<TranslationDto>?,
    @SerializedName("audio") val audio: AudioDto?
)

data class AudioDto(
    @SerializedName("url") val url: String,
    @SerializedName("segments") val segments: List<List<Int>>?
)

data class TranslationDto(
    @SerializedName("id") val id: Int,
    @SerializedName("resource_id") val resourceId: Int,
    @SerializedName("text") val text: String
)
