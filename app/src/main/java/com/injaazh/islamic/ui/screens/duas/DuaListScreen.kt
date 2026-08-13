package com.injaazh.islamic.ui.screens.duas

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
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
import com.injaazh.islamic.data.local.entity.DuaEntity

import androidx.compose.ui.tooling.preview.Preview
import com.injaazh.islamic.ui.theme.InjaazhIslamicTheme

@Preview(showBackground = true)
@Composable
fun DuaListPreview() {
    InjaazhIslamicTheme {
        DuaListScreenContent(
            categoryName = "সকাল-সন্ধ্যার জিকির",
            duas = listOf(
                DuaEntity(1, 1, "أَصْبَحْنَا وَأَصْبَحَ الْمُلْكُ لِلَّهِ", "আসবাহনা...", "আমরা ভোরে উপনীত হয়েছি...", "Morning...", "Muslim"),
                DuaEntity(2, 1, "اللَّهُمَّ بِكَ أَصْبَحْنَا", "আল্লাহুম্মা বিকা...", "হে আল্লাহ! আপনার দয়ায়...", "O Allah...", "Tirmidhi")
            ),
            onBackClick = {},
            onFavoriteClick = {}
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DuaListScreen(
    categoryId: Int,
    categoryName: String,
    viewModel: DuaViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {
    val duas by viewModel.duas.collectAsState()

    LaunchedEffect(categoryId) {
        viewModel.loadDuasByCategory(categoryId)
    }

    DuaListScreenContent(
        categoryName = categoryName,
        duas = duas,
        onBackClick = onBackClick,
        onFavoriteClick = { viewModel.toggleFavorite(it) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DuaListScreenContent(
    categoryName: String,
    duas: List<DuaEntity>,
    onBackClick: () -> Unit,
    onFavoriteClick: (DuaEntity) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = categoryName, fontWeight = FontWeight.Bold) },
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
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(duas) { dua ->
                DuaItem(dua, onFavoriteClick = { onFavoriteClick(dua) })
            }
        }
    }
}

@Composable
fun DuaItem(dua: DuaEntity, onFavoriteClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = onFavoriteClick) {
                    Icon(
                        imageVector = if (dua.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = if (dua.isFavorite) Color.Red else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                    )
                }
            }

            // Arabic
            Text(
                text = dua.arabicText,
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.headlineMedium.copy(
                    lineHeight = 44.sp,
                    textAlign = TextAlign.End
                ),
                color = MaterialTheme.colorScheme.primary
            )

            // Transliteration
            dua.transliterationBn?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyMedium.copy(lineHeight = 24.sp),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }

            // Translation
            dua.translationBn?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyLarge.copy(lineHeight = 28.sp),
                    fontWeight = FontWeight.Medium
                )
            }

            // Reference
            dua.reference?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f)
                )
            }
        }
    }
}
