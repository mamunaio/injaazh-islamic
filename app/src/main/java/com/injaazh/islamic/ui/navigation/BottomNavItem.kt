package com.injaazh.islamic.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val route: String,
    val icon: ImageVector,
    val label: String
) {
    object Dashboard : BottomNavItem(Screen.Dashboard.route, Icons.Default.Home, "হোম")
    object Tracker : BottomNavItem(Screen.Tracker.route, Icons.Default.Analytics, "ট্র্যাকার")
    object Quran : BottomNavItem(Screen.Quran.route, Icons.Default.MenuBook, "কুরআন")
    object Education : BottomNavItem(Screen.Education.route, Icons.Default.School, "শিক্ষা")
    object Settings : BottomNavItem(Screen.Settings.route, Icons.Default.Settings, "সেটিংস")
}
