package com.injaazh.islamic.ui.screens.quran

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.injaazh.islamic.data.remote.model.AudioDto
import com.injaazh.islamic.data.remote.model.TranslationDto
import com.injaazh.islamic.data.remote.model.VerseDto

import androidx.compose.ui.tooling.preview.Preview
import com.injaazh.islamic.ui.theme.InjaazhIslamicTheme

@Preview(showBackground = true)
@Composable
fun AyahPreview() {
    InjaazhIslamicTheme {
        AyahScreenContent(
            chapterName = "Al-Fatihah",
            verses = listOf(
                VerseDto(1, 1, "1:1", "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ", listOf(TranslationDto(1, 161, "সব প্রশংসা আল্লাহর জন্য, যিনি জগৎসমূহের প্রতিপালক।")), null),
                VerseDto(2, 2, "1:2", "الْحَمْدُ لِلَّهِ رَبِّ الْعَالَمِينَ", listOf(TranslationDto(2, 161, "পরম করুণাময়, অতি দয়ালু।")), null)
            ),
            isLoading = false,
            currentMediaItem = null,
            isPlaying = false,
            onBackClick = {},
            onPlayClick = {},
            onTogglePlay = {}
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AyahScreen(
    chapterId: Int,
    chapterName: String,
    viewModel: QuranViewModel = hiltViewModel(),
    audioViewModel: AudioViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {
    val verses by viewModel.verses.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val currentMediaItem by audioViewModel.currentMediaItem.collectAsState()
    val isPlaying by audioViewModel.isPlaying.collectAsState()

    LaunchedEffect(chapterId) {
        viewModel.loadVerses(chapterId)
    }

    AyahScreenContent(
        chapterName = chapterName,
        verses = verses,
        isLoading = isLoading,
        currentMediaItem = currentMediaItem,
        isPlaying = isPlaying,
        onBackClick = onBackClick,
        onPlayClick = { verse ->
            verse.audio?.url?.let { url ->
                audioViewModel.playAyah(url, "আয়াত ${verse.verseKey}", chapterName)
            }
        },
        onTogglePlay = { if (isPlaying) audioViewModel.pause() else audioViewModel.resume() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AyahScreenContent(
    chapterName: String,
    verses: List<VerseDto>,
    isLoading: Boolean,
    currentMediaItem: androidx.media3.common.MediaItem?,
    isPlaying: Boolean,
    onBackClick: () -> Unit,
    onPlayClick: (VerseDto) -> Unit,
    onTogglePlay: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = chapterName, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        },
        bottomBar = {
            if (currentMediaItem != null) {
                MiniPlayer(
                    title = currentMediaItem.mediaMetadata.title?.toString() ?: "",
                    isPlaying = isPlaying,
                    onTogglePlay = onTogglePlay
                )
            }
        }
    ) { padding ->
        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                items(verses) { verse ->
                    val isCurrentAyah = currentMediaItem?.mediaId == (if (verse.audio?.url?.startsWith("http") == true) verse.audio.url else "https:${verse.audio?.url}")
                    
                    AyahItem(
                        verse = verse,
                        isHighlighted = isCurrentAyah,
                        onPlayClick = { onPlayClick(verse) }
                    )
                }
            }
        }
    }
}

@Composable
fun AyahItem(
    verse: VerseDto,
    isHighlighted: Boolean = false,
    onPlayClick: () -> Unit
) {
    Surface(
        color = if (isHighlighted) MaterialTheme.colorScheme.primary.copy(alpha = 0.05f) else Color.Transparent,
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Verse Number
                Surface(
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = verse.verseKey,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                }

                IconButton(onClick = onPlayClick) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "Play",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            // Arabic Text
            Text(
                text = verse.textUthmani,
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.headlineMedium.copy(
                    lineHeight = 48.sp,
                    textAlign = TextAlign.End
                ),
                color = if (isHighlighted) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
            )

            // Translation
            verse.translations?.firstOrNull()?.let { translation ->
                Text(
                    text = translation.text.replace(Regex("<.*?>"), ""),
                    style = MaterialTheme.typography.bodyLarge.copy(lineHeight = 28.sp),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                )
            }
            
            HorizontalDivider(modifier = Modifier.padding(top = 12.dp), color = MaterialTheme.colorScheme.outlineVariant)
        }
    }
}

@Composable
fun MiniPlayer(
    title: String,
    isPlaying: Boolean,
    onTogglePlay: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp),
        color = MaterialTheme.colorScheme.surfaceVariant,
        tonalElevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "এখন বাজছে",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1
                )
            }

            IconButton(onClick = onTogglePlay) {
                Icon(
                    imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                    contentDescription = "Toggle Play",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}
