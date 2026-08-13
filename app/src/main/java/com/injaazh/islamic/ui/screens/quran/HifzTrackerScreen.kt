package com.injaazh.islamic.ui.screens.quran

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.injaazh.islamic.R
import com.injaazh.islamic.data.local.entity.HifzEntity
import com.injaazh.islamic.ui.screens.tracker.CircularProgressChart

import androidx.compose.ui.tooling.preview.Preview
import com.injaazh.islamic.ui.theme.InjaazhIslamicTheme

@Preview(showBackground = true)
@Composable
fun HifzTrackerPreview() {
    InjaazhIslamicTheme {
        HifzTrackerContent(
            records = listOf(
                HifzEntity(1, "Al-Fatihah", "الفاتحة", 7, 7, true),
                HifzEntity(2, "Al-Baqarah", "البقرة", 286, 10, false)
            ),
            totalMemorized = 17,
            totalAyahs = 6236,
            onUpdateProgress = { _, _ -> }
        )
    }
}

@Composable
fun HifzTrackerScreen(
    viewModel: HifzViewModel = hiltViewModel()
) {
    val records by viewModel.hifzRecords.collectAsState()
    val totalMemorized by viewModel.totalMemorized.collectAsState()
    val totalAyahs = 6236

    HifzTrackerContent(
        records = records,
        totalMemorized = totalMemorized,
        totalAyahs = totalAyahs,
        onUpdateProgress = { id, count -> viewModel.updateProgress(id, count) }
    )
}

@Composable
fun HifzTrackerContent(
    records: List<HifzEntity>,
    totalMemorized: Int,
    totalAyahs: Int,
    onUpdateProgress: (Int, Int) -> Unit
) {
    Scaffold(
        topBar = {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.primary,
                tonalElevation = 4.dp
            ) {
                Text(
                    text = "হিফজ ট্র্যাকার",
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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                HifzOverallProgressCard(totalMemorized, totalAyahs)
            }

            item {
                Text(
                    text = "সুরা ভিত্তিক অগ্রগতি",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            items(records) { record ->
                HifzSurahItem(record) { newCount ->
                    onUpdateProgress(record.surahId, newCount)
                }
            }
        }
    }
}

@Composable
fun HifzOverallProgressCard(memorized: Int, total: Int) {
    val progress = if (total > 0) memorized.toFloat() / total else 0f
    
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
                    text = "কুরআন হিফজ অগ্রগতি",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
                Text(
                    text = "$memorized / $total আয়াত",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "${String.format("%.2f", progress * 100)}% সম্পন্ন",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}

@Composable
fun HifzSurahItem(record: HifzEntity, onUpdate: (Int) -> Unit) {
    var showDialog by remember { mutableStateOf(false) }
    val progress = if (record.totalAyahs > 0) record.memorizedAyahs.toFloat() / record.totalAyahs else 0f

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { showDialog = true },
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 1.dp
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Small Progress Ring
            Box(contentAlignment = Alignment.Center, modifier = Modifier.size(50.dp)) {
                CircularProgressIndicator(
                    progress = { 1f },
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    strokeWidth = 4.dp,
                    strokeCap = StrokeCap.Round
                )
                CircularProgressIndicator(
                    progress = { progress },
                    modifier = Modifier.fillMaxSize(),
                    color = if (record.isCompleted) Color(0xFF4CAF50) else MaterialTheme.colorScheme.primary,
                    strokeWidth = 4.dp,
                    strokeCap = StrokeCap.Round
                )
                Text(
                    text = "${(progress * 100).toInt()}%",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = record.surahNameBn,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${record.memorizedAyahs} / ${record.totalAyahs} আয়াত",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }

            Text(
                text = record.surahNameAr,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }

    if (showDialog) {
        HifzUpdateDialog(
            record = record,
            onDismiss = { showDialog = false },
            onConfirm = { 
                onUpdate(it)
                showDialog = false
            }
        )
    }
}

@Composable
fun HifzUpdateDialog(
    record: HifzEntity,
    onDismiss: () -> Unit,
    onConfirm: (Int) -> Unit
) {
    var sliderValue by remember { mutableStateOf(record.memorizedAyahs.toFloat()) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "${record.surahNameBn} প্রগতি") },
        text = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "${sliderValue.toInt()} আয়াত মুখস্থ হয়েছে",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(16.dp))
                Slider(
                    value = sliderValue,
                    onValueChange = { sliderValue = it },
                    valueRange = 0f..record.totalAyahs.toFloat(),
                    steps = record.totalAyahs - 1
                )
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = "০", style = MaterialTheme.typography.bodySmall)
                    Text(text = "${record.totalAyahs}", style = MaterialTheme.typography.bodySmall)
                }
            }
        },
        confirmButton = {
            Button(onClick = { onConfirm(sliderValue.toInt()) }) {
                Text("আপডেট করুন")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("বাতিল")
            }
        }
    )
}
