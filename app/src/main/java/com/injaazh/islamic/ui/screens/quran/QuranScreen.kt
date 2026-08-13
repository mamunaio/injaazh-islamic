package com.injaazh.islamic.ui.screens.quran

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.injaazh.islamic.R
import com.injaazh.islamic.data.remote.model.ChapterDto
import com.injaazh.islamic.data.remote.model.TranslatedName

import androidx.compose.ui.tooling.preview.Preview
import com.injaazh.islamic.ui.theme.InjaazhIslamicTheme

@Preview(showBackground = true)
@Composable
fun QuranPreview() {
    InjaazhIslamicTheme {
        QuranScreenContent(
            chapters = listOf(
                ChapterDto(1, "Meccan", "Al-Fatihah", "الفاتحة", 7, TranslatedName("bn", "সূচনা")),
                ChapterDto(2, "Medinan", "Al-Baqarah", "البقرة", 286, TranslatedName("bn", "গাভী"))
            ),
            isLoading = false,
            onChapterClick = { _, _ -> }
        )
    }
}

@Composable
fun QuranScreen(
    viewModel: QuranViewModel = hiltViewModel(),
    onChapterClick: (Int, String) -> Unit
) {
    val chapters by viewModel.chapters.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    QuranScreenContent(chapters, isLoading, onChapterClick)
}

@Composable
fun QuranScreenContent(
    chapters: List<ChapterDto>,
    isLoading: Boolean,
    onChapterClick: (Int, String) -> Unit
) {
    Scaffold(
        topBar = {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.primary,
                tonalElevation = 4.dp
            ) {
                Text(
                    text = stringResource(R.string.quran_title),
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
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(chapters) { chapter ->
                    ChapterItem(chapter) {
                        onChapterClick(chapter.id, chapter.nameSimple)
                    }
                }
            }
        }
    }
}

@Composable
fun ChapterItem(chapter: ChapterDto, onClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        tonalElevation = 1.dp,
        color = MaterialTheme.colorScheme.surface
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Chapter Number
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = chapter.id.toString(),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = chapter.nameSimple,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${chapter.translatedName.name} • ${chapter.versesCount} আয়াত",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }

            Text(
                text = chapter.nameArabic,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Medium
            )
        }
    }
}
