package com.injaazh.islamic.ui.screens.tracker

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.injaazh.islamic.R
import com.injaazh.islamic.data.local.entity.PrayerTrackerEntity

import androidx.compose.ui.tooling.preview.Preview
import com.injaazh.islamic.ui.theme.InjaazhIslamicTheme

@Preview(showBackground = true)
@Composable
fun TrackerPreview() {
    InjaazhIslamicTheme {
        TrackerScreenContent(
            todayTracker = PrayerTrackerEntity(
                date = "2026-08-12",
                fajr = true,
                dhuhr = false
            ),
            progress = 0.4f,
            onToggle = {}
        )
    }
}

@Composable
fun TrackerScreen(
    viewModel: TrackerViewModel = hiltViewModel()
) {
    val todayTracker by viewModel.todayTracker.collectAsState()
    val progress by viewModel.overallProgress.collectAsState()

    TrackerScreenContent(
        todayTracker = todayTracker,
        progress = progress,
        onToggle = { viewModel.togglePrayer(it) }
    )
}

@Composable
fun TrackerScreenContent(
    todayTracker: PrayerTrackerEntity?,
    progress: Float,
    onToggle: (String) -> Unit
) {
    Scaffold(
        topBar = {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.primary,
                tonalElevation = 4.dp
            ) {
                Text(
                    text = stringResource(R.string.tracker_title),
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White,
                    modifier = Modifier
                        .statusBarsPadding()
                        .padding(16.dp),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.05f),
                            MaterialTheme.colorScheme.background
                        )
                    )
                ),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item {
                OverallProgressCard(progress)
            }

            item {
                Text(
                    text = "আজকের নামাজ",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            item {
                todayTracker?.let { tracker ->
                    TodayTrackerCard(tracker) { prayer ->
                        onToggle(prayer)
                    }
                }
            }
        }
    }
}

@Composable
fun OverallProgressCard(progress: Float) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            CircularProgressChart(progress = progress)
            
            Column {
                Text(
                    text = "সামগ্রিক অগ্রগতি",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
                Text(
                    text = "${(progress * 100).toInt()}% সম্পন্ন",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
fun CircularProgressChart(progress: Float) {
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(durationMillis = 1000), label = ""
    )
    
    Box(contentAlignment = Alignment.Center, modifier = Modifier.size(100.dp)) {
        Canvas(modifier = Modifier.size(100.dp)) {
            drawCircle(
                color = Color.LightGray.copy(alpha = 0.2f),
                style = Stroke(width = 12.dp.toPx())
            )
            drawArc(
                color = Color(0xFF00695C),
                startAngle = -90f,
                sweepAngle = 360 * animatedProgress,
                useCenter = false,
                style = Stroke(width = 12.dp.toPx(), cap = StrokeCap.Round)
            )
        }
        Text(
            text = "${(animatedProgress * 100).toInt()}%",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = Color(0xFF00695C)
        )
    }
}

@Composable
fun TodayTrackerCard(tracker: PrayerTrackerEntity, onToggle: (String) -> Unit) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        PrayerToggleItem("ফজর", tracker.fajr) { onToggle("Fajr") }
        PrayerToggleItem("যোহর", tracker.dhuhr) { onToggle("Dhuhr") }
        PrayerToggleItem("আসর", tracker.asr) { onToggle("Asr") }
        PrayerToggleItem("মাগরিব", tracker.maghrib) { onToggle("Maghrib") }
        PrayerToggleItem("এশা", tracker.isha) { onToggle("Isha") }
    }
}

@Composable
fun PrayerToggleItem(name: String, isDone: Boolean, onClick: () -> Unit) {
    Surface(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = if (isDone) MaterialTheme.colorScheme.primary.copy(alpha = 0.1f) else MaterialTheme.colorScheme.surface,
        border = if (isDone) androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)) else null,
        tonalElevation = 1.dp
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Icon(
                    imageVector = if (isDone) Icons.Default.CheckCircle else Icons.Outlined.Circle,
                    contentDescription = null,
                    tint = if (isDone) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                )
                Text(
                    text = name,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = if (isDone) FontWeight.Bold else FontWeight.Medium
                )
            }
            if (isDone) {
                Text(
                    text = "পড়া হয়েছে",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
