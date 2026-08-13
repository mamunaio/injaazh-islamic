package com.injaazh.islamic.domain.model

import java.util.Date

data class PrayerTime(
    val name: String,
    val time: Date,
    val isNext: Boolean = false
)

data class PrayerTimesState(
    val fajr: PrayerTime? = null,
    val sunrise: PrayerTime? = null,
    val dhuhr: PrayerTime? = null,
    val asr: PrayerTime? = null,
    val maghrib: PrayerTime? = null,
    val isha: PrayerTime? = null,
    val nextPrayer: PrayerTime? = null,
    val locationName: String = "Unknown Location"
)
