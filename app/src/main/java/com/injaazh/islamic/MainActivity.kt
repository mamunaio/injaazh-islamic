package com.injaazh.islamic

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.injaazh.islamic.ui.navigation.BottomNavItem
import com.injaazh.islamic.ui.navigation.NavGraph
import com.injaazh.islamic.ui.navigation.Screen
import com.injaazh.islamic.ui.theme.InjaazhIslamicTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            InjaazhIslamicTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                
                val showBottomBar = currentDestination?.route != Screen.Splash.route

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        if (showBottomBar) {
                            NavigationBar(
                                containerColor = MaterialTheme.colorScheme.surface,
                                tonalElevation = 8.dp
                            ) {
                                val items = listOf(
                                    BottomNavItem.Dashboard,
                                    BottomNavItem.Tracker,
                                    BottomNavItem.Quran,
                                    BottomNavItem.Education,
                                    BottomNavItem.Settings
                                )
                                items.forEach { item ->
                                    val label = when(item) {
                                        BottomNavItem.Dashboard -> stringResource(R.string.nav_home)
                                        BottomNavItem.Tracker -> stringResource(R.string.tracker_title)
                                        BottomNavItem.Quran -> stringResource(R.string.nav_quran)
                                        BottomNavItem.Education -> "শিক্ষা"
                                        BottomNavItem.Settings -> "সেটিংস"
                                    }
                                    NavigationBarItem(
                                        icon = { Icon(item.icon, contentDescription = label) },
                                        label = { Text(label, style = MaterialTheme.typography.labelSmall) },
                                        selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                                        colors = NavigationBarItemDefaults.colors(
                                            selectedIconColor = MaterialTheme.colorScheme.primary,
                                            selectedTextColor = MaterialTheme.colorScheme.primary,
                                            unselectedIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                                            unselectedTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                                            indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                                        ),
                                        onClick = {
                                            navController.navigate(item.route) {
                                                popUpTo(navController.graph.findStartDestination().id) {
                                                    saveState = true
                                                }
                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    }
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        NavGraph(navController = navController)
                    }
                }
            }
        }
    }
}
