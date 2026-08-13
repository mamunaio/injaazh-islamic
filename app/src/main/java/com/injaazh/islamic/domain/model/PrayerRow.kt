package com.injaazh.islamic.domain.model

import androidx.compose.ui.graphics.vector.ImageVector

data class PrayerRow(
    val id: String,
    val name: String,
    val timeRange: String,
    val icon: ImageVector?,
    val type: PrayerRowType,
    val tag: String? = null
)

enum class PrayerRowType {
    STANDARD,
    EVENT,      // Sunrise, Sunset
    FORBIDDEN   // Makruh Times
}
