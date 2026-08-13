package com.injaazh.islamic.ui.screens.prayer

import androidx.compose.animation.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.injaazh.islamic.R
import com.injaazh.islamic.domain.model.PrayerRow
import com.injaazh.islamic.domain.model.PrayerRowType
import com.injaazh.islamic.ui.theme.InjaazhIslamicTheme
import java.text.SimpleDateFormat
import java.time.chrono.HijrahDate
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrayerTimesScreen(
    viewModel: PrayerTimesViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {
    val rows by viewModel.prayerRows.collectAsState()
    val selectedDate by viewModel.selectedDate.collectAsState()
    val currentTime by viewModel.currentTime.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("নামাজের সময়", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { /* Info */ }) {
                        Icon(Icons.Default.Info, contentDescription = "Info")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White,
                    actionIconContentColor = Color.White
                )
            )
        },
        containerColor = Color(0xFF121212) // Dark Theme Base
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 2. Hero Section (Dynamic Top Card)
            item {
                PrayerHeroCard("যোহর", "00:45:12", "যোহর - সুন্নাত: ৪, ফরয: ৪, সুন্নাত: ২, নফল: ২", "পাবনা, রাজশাহী, বাংলাদেশ")
            }

            // 3. Date Navigator
            item {
                DateNavigator(
                    date = selectedDate,
                    onPrev = { viewModel.changeDate(-1) },
                    onNext = { viewModel.changeDate(1) }
                )
            }

            // 4. Comprehensive Prayer List
            items(rows) { row ->
                PrayerTimeRowItem(row)
            }

            // 5. Bottom Action Button
            item {
                Button(
                    onClick = { /* Yearly Calendar */ },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f))
                ) {
                    Text("বছরের নামাজের ক্যালেন্ডার দেখুন", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                }
            }
            
            item { Spacer(modifier = Modifier.height(20.dp)) }
        }
    }
}

@Composable
fun PrayerHeroCard(name: String, remaining: String, rakat: String, location: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(32.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color(0xFF004D40), Color(0xFF00695C))
                    )
                )
                .padding(24.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                Text(text = "এখন: $name", color = Color.White.copy(alpha = 0.8f), style = MaterialTheme.typography.labelLarge)
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Visual Countdown
                Box(contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(
                        progress = { 0.7f },
                        modifier = Modifier.size(160.dp),
                        strokeWidth = 12.dp,
                        color = Color(0xFFFFD54F),
                        trackColor = Color.White.copy(alpha = 0.1f),
                        strokeCap = StrokeCap.Round
                    )
                    Text(
                        text = remaining,
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.White,
                        fontWeight = FontWeight.ExtraBold,
                        fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
                
                Text(text = rakat, color = Color.White.copy(alpha = 0.9f), style = MaterialTheme.typography.bodySmall, textAlign = TextAlign.Center)
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Place, contentDescription = null, tint = Color(0xFFFFD54F), modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = location, color = Color.White.copy(alpha = 0.6f), style = MaterialTheme.typography.labelSmall)
                }
            }
        }
    }
}

@Composable
fun DateNavigator(date: Date, onPrev: () -> Unit, onNext: () -> Unit) {
    val englishDate = SimpleDateFormat("d MMMM yyyy", Locale.ENGLISH).format(date)
    val hijriDate = HijrahDate.now().toString() // Simplified for UI

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onPrev) { Icon(Icons.Default.ChevronLeft, contentDescription = null, tint = Color.White) }
        
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = englishDate, color = Color.White, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyLarge)
            Text(text = hijriDate, color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.labelSmall)
        }
        
        IconButton(onClick = onNext) { Icon(Icons.Default.ChevronRight, contentDescription = null, tint = Color.White) }
    }
}

@Composable
fun PrayerTimeRowItem(row: PrayerRow) {
    val bgColor = when(row.type) {
        PrayerRowType.EVENT -> Color(0xFF3E2723).copy(alpha = 0.3f)
        PrayerRowType.FORBIDDEN -> Color(0xFFB71C1C).copy(alpha = 0.2f)
        else -> Color.White.copy(alpha = 0.05f)
    }
    
    val accentColor = when(row.type) {
        PrayerRowType.FORBIDDEN -> Color(0xFFFF5252)
        else -> MaterialTheme.colorScheme.primary
    }

    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = bgColor,
        shape = RoundedCornerShape(16.dp),
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            row.icon?.let {
                Icon(it, contentDescription = null, tint = accentColor, modifier = Modifier.size(24.dp))
            }
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = row.name, color = Color.White, fontWeight = FontWeight.Bold)
                    if (row.tag != null) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Surface(color = accentColor.copy(alpha = 0.1f), shape = RoundedCornerShape(4.dp)) {
                            Text(text = row.tag, modifier = Modifier.padding(horizontal = 4.dp), color = accentColor, fontSize = 10.sp)
                        }
                    }
                }
            }
            
            Text(text = row.timeRange, color = Color.White.copy(alpha = 0.8f), style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true, backgroundColor = 0xFF121212)
@Composable
fun PrayerTimesPreview() {
    InjaazhIslamicTheme {
        PrayerTimesScreen(onBackClick = {})
    }
}
