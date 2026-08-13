package com.injaazh.islamic.ui.screens.dashboard

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.injaazh.islamic.R
import com.injaazh.islamic.domain.model.PrayerTime
import com.injaazh.islamic.domain.model.PrayerTimesState
import com.injaazh.islamic.util.formatToTime
import java.text.SimpleDateFormat
import java.time.chrono.HijrahDate
import java.util.Date
import java.util.Locale

import com.injaazh.islamic.ui.navigation.Screen
import com.injaazh.islamic.ui.theme.InjaazhIslamicTheme

@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = hiltViewModel(),
    onNavigate: (String) -> Unit = {}
) {
    val state by viewModel.uiState.collectAsState()
    val currentTime by viewModel.currentTime.collectAsState()
    val tracker by viewModel.todayTracker.collectAsState()
    
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) viewModel.refreshLocation()
    }

    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
        permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    DashboardContent(
        state = state,
        currentTime = currentTime,
        tracker = tracker,
        onRefreshLocation = { viewModel.refreshLocation() },
        onToggleTracker = { viewModel.togglePrayer(it) },
        onNavigate = onNavigate
    )
}

@Composable
fun DashboardContent(
    state: PrayerTimesState,
    currentTime: Long,
    tracker: com.injaazh.islamic.data.local.entity.PrayerTrackerEntity?,
    onRefreshLocation: () -> Unit,
    onToggleTracker: (String) -> Unit,
    onNavigate: (String) -> Unit
) {
    Scaffold(
        topBar = {
            DashboardTopBar(state.locationName, onRefreshLocation)
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // 1. Top Header Section
            item { GreetingSection() }

            // 2. Dynamic Next Prayer Card
            item { NextPrayerCountdownCard(state, currentTime) }

            // 3. Compact Prayer Tracker
            item {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(
                        text = stringResource(R.string.tracker_title),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    tracker?.let {
                        CompactPrayerTracker(it, onToggleTracker)
                    }
                }
            }

            // 4. Quick Access Feature Grid
            item { FeatureGrid(onNavigate) }

            // 5. Daily Inspiration Card
            item { DailyInspirationCard() }
            
            item { Spacer(modifier = Modifier.height(20.dp)) }
        }
    }
}

@Composable
fun GreetingSection() {
    val hijriDate = HijrahDate.now()
    val englishDate = SimpleDateFormat("EEEE, d MMMM yyyy", Locale.ENGLISH).format(Date())
    val bengaliDate = SimpleDateFormat("d MMMM yyyy", Locale("bn")).format(Date())

    Column {
        Text(
            text = stringResource(R.string.greeting_msg),
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "$englishDate | $bengaliDate",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
        )
        Text(
            text = hijriDate.toString(),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.secondary
        )
    }
}

@Composable
fun NextPrayerCountdownCard(state: PrayerTimesState, currentTimeMillis: Long) {
    val now = Date(currentTimeMillis)
    val nextPrayer = listOfNotNull(
        state.fajr, state.dhuhr, state.asr, state.maghrib, state.isha
    ).filter { it.time.after(now) }.minByOrNull { it.time }

    val diff = nextPrayer?.time?.time?.minus(currentTimeMillis) ?: 0L
    val hours = (diff / (1000 * 60 * 60)) % 24
    val minutes = (diff / (1000 * 60)) % 60
    val seconds = (diff / 1000) % 60

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .background(
                    Brush.linearGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.secondary
                        )
                    )
                )
                .padding(24.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = if (nextPrayer != null) "${nextPrayer.name} এর বাকি" else "পরবর্তী নামাজ",
                    color = Color.White.copy(alpha = 0.8f),
                    style = MaterialTheme.typography.labelLarge
                )
                
                Text(
                    text = stringResource(R.string.remaining_time, hours, minutes, seconds),
                    color = Color.White,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace
                )

                Spacer(modifier = Modifier.height(16.dp))
                
                HorizontalDivider(color = Color.White.copy(alpha = 0.2f))
                
                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    PrayerSubInfo(stringResource(R.string.fajr), state.fajr?.time?.formatToTime() ?: "--")
                    PrayerSubInfo("ইফতার", state.maghrib?.time?.formatToTime() ?: "--")
                }
            }
        }
    }
}

@Composable
fun PrayerSubInfo(label: String, time: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = label, color = Color.White.copy(alpha = 0.7f), style = MaterialTheme.typography.labelSmall)
        Text(text = time, color = Color.White, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
fun CompactPrayerTracker(tracker: com.injaazh.islamic.data.local.entity.PrayerTrackerEntity, onToggle: (String) -> Unit) {
    val prayers = listOf(
        Triple("ফজর", tracker.fajr, "Fajr"),
        Triple("যোহর", tracker.dhuhr, "Dhuhr"),
        Triple("আসর", tracker.asr, "Asr"),
        Triple("মাগরিব", tracker.maghrib, "Maghrib"),
        Triple("এশা", tracker.isha, "Isha")
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        prayers.forEach { (name, isDone, id) ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(16.dp))
                    .clickable { onToggle(id) }
                    .background(
                        if (isDone) MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                        else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                    )
                    .padding(vertical = 12.dp)
            ) {
                Icon(
                    imageVector = if (isDone) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
                    contentDescription = null,
                    tint = if (isDone) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = name,
                    style = MaterialTheme.typography.labelSmall,
                    color = if (isDone) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                    fontWeight = if (isDone) FontWeight.Bold else FontWeight.Normal
                )
            }
            if (name != "এশা") Spacer(modifier = Modifier.width(8.dp))
        }
    }
}

@Composable
fun FeatureGrid(onNavigate: (String) -> Unit) {
    val items = listOf(
        FeatureItem("নামাজের সময়", Icons.Default.Schedule, Screen.PrayerTimes.route),
        FeatureItem("আল-কুরআন", Icons.Default.MenuBook, Screen.Quran.route),
        FeatureItem("হাদিস", Icons.Default.AutoStories, "hadith"),
        FeatureItem("দোয়া", Icons.Default.Favorite, Screen.Duas.route),
        FeatureItem("কিবলা", Icons.Default.Explore, Screen.Qibla.route),
        FeatureItem("তাসবিহ", Icons.Default.Fingerprint, Screen.Tasbih.route),
        FeatureItem("যাকাত", Icons.Default.Calculate, Screen.Zakat.route),
        FeatureItem("মসজিদ", Icons.Default.Place, "mosque")
    )

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            items.take(4).forEach { item -> GridIcon(item, onNavigate) }
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            items.drop(4).forEach { item -> GridIcon(item, onNavigate) }
        }
    }
}

@Composable
fun GridIcon(item: FeatureItem, onNavigate: (String) -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(80.dp)
            .clickable { onNavigate(item.route) }
    ) {
        Surface(
            modifier = Modifier.size(56.dp),
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = item.name,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(28.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = item.name,
            style = MaterialTheme.typography.labelSmall,
            textAlign = TextAlign.Center,
            maxLines = 1
        )
    }
}

@Composable
fun DailyInspirationCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.FormatQuote,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(R.string.daily_inspiration),
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "وَاسْتَعِينُوا بِالصَّبْرِ وَالصَّلَاةِ",
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "তোমরা ধৈর্য ও সালাতের মাধ্যমে সাহায্য প্রার্থনা কর।",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }
    }
}

data class FeatureItem(val name: String, val icon: ImageVector, val route: String)

@Composable
fun DashboardTopBar(locationName: String, onRefresh: () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color.Transparent
    ) {
        Row(
            modifier = Modifier
                .statusBarsPadding()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable { onRefresh() }) {
                Icon(Icons.Default.Place, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = if (locationName == "Unknown Location") stringResource(R.string.location_default) else locationName,
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
            IconButton(onClick = { /* TODO: Notification History */ }) {
                Icon(Icons.Default.NotificationsNone, contentDescription = null)
            }
        }
    }
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
@Composable
fun DashboardRedesignPreview() {
    InjaazhIslamicTheme {
        DashboardContent(
            state = PrayerTimesState(
                locationName = "Dhaka",
                fajr = PrayerTime("ফজর", Date()),
                maghrib = PrayerTime("মাগরিব", Date())
            ),
            currentTime = System.currentTimeMillis(),
            tracker = com.injaazh.islamic.data.local.entity.PrayerTrackerEntity("2026-08-13", fajr = true),
            onRefreshLocation = {},
            onToggleTracker = {},
            onNavigate = {}
        )
    }
}
