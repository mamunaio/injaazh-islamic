package com.injaazh.islamic.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.injaazh.islamic.ui.screens.dashboard.DashboardScreen
import com.injaazh.islamic.ui.screens.duas.DuaCategoryScreen
import com.injaazh.islamic.ui.screens.duas.DuaListScreen
import com.injaazh.islamic.ui.screens.education.AsmaulHusnaScreen
import com.injaazh.islamic.ui.screens.education.EducationScreen
import com.injaazh.islamic.ui.screens.prayer.PrayerTimesScreen
import com.injaazh.islamic.ui.screens.qibla.QiblaScreen
import com.injaazh.islamic.ui.screens.quran.AyahScreen
import com.injaazh.islamic.ui.screens.quran.HifzTrackerScreen
import com.injaazh.islamic.ui.screens.quran.QuranScreen
import com.injaazh.islamic.ui.screens.settings.SettingsScreen
import com.injaazh.islamic.ui.screens.splash.SplashScreen
import com.injaazh.islamic.ui.screens.tasbih.TasbihScreen
import com.injaazh.islamic.ui.screens.tracker.TrackerScreen
import com.injaazh.islamic.ui.screens.zakat.ZakatScreen

import android.net.Uri

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Dashboard : Screen("dashboard")
    object Tracker : Screen("tracker")
    object PrayerTimes : Screen("prayer_times")
    object Tasbih : Screen("tasbih")
    object Zakat : Screen("zakat")
    object Settings : Screen("settings")
    object Quran : Screen("quran")
    object Hifz : Screen("hifz")
    object Ayah : Screen("ayah/{chapterId}/{chapterName}") {
        fun createRoute(chapterId: Int, chapterName: String) = 
            "ayah/$chapterId/${Uri.encode(chapterName)}"
    }
    object Duas : Screen("duas")
    object DuaList : Screen("dua_list/{categoryId}/{categoryName}") {
        fun createRoute(categoryId: Int, categoryName: String) = 
            "dua_list/$categoryId/${Uri.encode(categoryName)}"
    }
    object Education : Screen("education")
    object AsmaulHusna : Screen("asmaul_husna")
    object Qibla : Screen("qibla")
}

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(onSplashFinished = {
                navController.navigate(Screen.Dashboard.route) {
                    popUpTo(Screen.Splash.route) { inclusive = true }
                }
            })
        }
        composable(Screen.Dashboard.route) {
            DashboardScreen(onNavigate = { route ->
                navController.navigate(route)
            })
        }
        composable(Screen.PrayerTimes.route) {
            PrayerTimesScreen(onBackClick = { navController.popBackStack() })
        }
        composable(Screen.Tracker.route) {
            TrackerScreen()
        }
        composable(Screen.Tasbih.route) {
            TasbihScreen()
        }
        composable(Screen.Zakat.route) {
            ZakatScreen()
        }
        composable(Screen.Settings.route) {
            SettingsScreen()
        }
        composable(Screen.Quran.route) {
            QuranScreen(onChapterClick = { id, name ->
                navController.navigate(Screen.Ayah.createRoute(id, name))
            })
        }
        composable(Screen.Hifz.route) {
            HifzTrackerScreen()
        }
        composable(
            route = Screen.Ayah.route,
            arguments = listOf(
                navArgument("chapterId") { type = NavType.IntType },
                navArgument("chapterName") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val chapterId = backStackEntry.arguments?.getInt("chapterId") ?: 1
            val chapterName = backStackEntry.arguments?.getString("chapterName") ?: ""
            AyahScreen(
                chapterId = chapterId,
                chapterName = chapterName,
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(Screen.Duas.route) {
            DuaCategoryScreen(onCategoryClick = { id, name ->
                navController.navigate(Screen.DuaList.createRoute(id, name))
            })
        }
        composable(
            route = Screen.DuaList.route,
            arguments = listOf(
                navArgument("categoryId") { type = NavType.IntType },
                navArgument("categoryName") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val categoryId = backStackEntry.arguments?.getInt("categoryId") ?: 1
            val categoryName = backStackEntry.arguments?.getString("categoryName") ?: ""
            DuaListScreen(
                categoryId = categoryId,
                categoryName = categoryName,
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(Screen.Education.route) {
            EducationScreen(onCategoryClick = { categoryId ->
                when(categoryId) {
                    "asmaul_husna" -> navController.navigate(Screen.AsmaulHusna.route)
                    "duas" -> navController.navigate(Screen.Duas.route)
                    "zakat" -> navController.navigate(Screen.Zakat.route)
                    "tasbih" -> navController.navigate(Screen.Tasbih.route)
                }
            })
        }
        composable(Screen.AsmaulHusna.route) {
            AsmaulHusnaScreen(onBackClick = { navController.popBackStack() })
        }
        composable(Screen.Qibla.route) {
            QiblaScreen()
        }
    }
}
