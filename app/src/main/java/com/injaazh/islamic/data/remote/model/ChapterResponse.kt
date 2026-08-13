package com.injaazh.islamic.data.remote.model

import com.google.gson.annotations.SerializedName

data class ChapterResponse(
    @SerializedName("chapters") val chapters: List<ChapterDto>
)

data class Chapter(
    @SerializedName("id") val id: Int,
    @SerializedName("revelation_place") val revelationPlace: String,
    @SerializedName("revelation_order") val revelationOrder: Int,
    @SerializedName("bismillah_pre") val bismillahPre: Boolean,
    @SerializedName("name_simple") val nameSimple: String,
    @SerializedName("name_complex") val nameComplex: String,
    @SerializedName("name_arabic") val nameArabic: String,
    @SerializedName("verses_count") val versesCount: Int,
    @SerializedName("translated_name") val translatedName: TranslatedName
)

data class ChapterDto(
    @SerializedName("id") val id: Int,
    @SerializedName("revelation_place") val revelationPlace: String,
    @SerializedName("name_simple") val nameSimple: String,
    @SerializedName("name_arabic") val nameArabic: String,
    @SerializedName("verses_count") val versesCount: Int,
    @SerializedName("translated_name") val translatedName: TranslatedName
)

data class TranslatedName(
    @SerializedName("language_name") val languageName: String,
    @SerializedName("name") val name: String
)
