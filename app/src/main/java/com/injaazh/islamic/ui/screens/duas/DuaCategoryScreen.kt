package com.injaazh.islamic.ui.screens.duas

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Nightlight
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.injaazh.islamic.R
import com.injaazh.islamic.data.local.entity.DuaCategoryEntity

import androidx.compose.ui.tooling.preview.Preview
import com.injaazh.islamic.ui.theme.InjaazhIslamicTheme

@Preview(showBackground = true)
@Composable
fun DuaCategoryPreview() {
    InjaazhIslamicTheme {
        DuaCategoryScreenContent(
            categories = listOf(
                DuaCategoryEntity(1, "Morning", "সকাল-সন্ধ্যার জিকির", "wb_sunny"),
                DuaCategoryEntity(2, "Sleep", "ঘুমের দোয়া", "bedtime"),
                DuaCategoryEntity(3, "Travel", "সফরের দোয়া", "commute")
            ),
            onCategoryClick = { _, _ -> }
        )
    }
}

@Composable
fun DuaCategoryScreen(
    viewModel: DuaViewModel = hiltViewModel(),
    onCategoryClick: (Int, String) -> Unit
) {
    val categories by viewModel.categories.collectAsState()
    DuaCategoryScreenContent(categories, onCategoryClick)
}

@Composable
fun DuaCategoryScreenContent(
    categories: List<DuaCategoryEntity>,
    onCategoryClick: (Int, String) -> Unit
) {
    Scaffold(
        topBar = {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.primary,
                tonalElevation = 4.dp
            ) {
                Text(
                    text = stringResource(R.string.duas_title),
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
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(categories) { category ->
                DuaCategoryItem(category) {
                    onCategoryClick(category.id, category.nameBn)
                }
            }
        }
    }
}

@Composable
fun DuaCategoryItem(category: DuaCategoryEntity, onClick: () -> Unit) {
    val icon = when (category.iconName) {
        "wb_sunny" -> Icons.Default.WbSunny
        "bedtime" -> Icons.Default.Nightlight
        else -> Icons.Default.Book
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clickable { onClick() },
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f), RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(32.dp)
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = category.nameBn,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                maxLines = 2
            )
        }
    }
}
