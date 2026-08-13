package com.injaazh.islamic.ui.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.injaazh.islamic.ui.theme.InjaazhIslamicTheme

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val adhanEnabled by viewModel.adhanEnabled.collectAsState()
    val currentLang = viewModel.currentLanguage

    Scaffold(
        topBar = {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.primary,
                tonalElevation = 4.dp
            ) {
                Text(
                    text = "সেটিংস",
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
                SettingsGroupTitle("ভাষা (Language)")
                SettingsCard {
                    LanguageToggle(
                        currentLang = currentLang,
                        onLangChange = { viewModel.setLanguage(it) }
                    )
                }
            }

            item {
                SettingsGroupTitle("নোটিফিকেশন")
                SettingsCard {
                    SettingsSwitchItem(
                        title = "আযান নোটিফিকেশন",
                        subtitle = "নামাজের সঠিক সময়ে আযান বাজবে",
                        icon = Icons.Default.Notifications,
                        checked = adhanEnabled,
                        onCheckedChange = { viewModel.toggleAdhan(it) }
                    )
                }
            }

            item {
                SettingsGroupTitle("অ্যাপ সম্পর্কে")
                SettingsCard {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "Injaazh Islamic", fontWeight = FontWeight.Bold)
                        Text(text = "Version 1.0.0", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
    }
}

@Composable
fun SettingsGroupTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.labelLarge,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(start = 8.dp, bottom = 8.dp)
    )
}

@Composable
fun SettingsCard(content: @Composable () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        content()
    }
}

@Composable
fun SettingsSwitchItem(
    title: String,
    subtitle: String,
    icon: ImageVector,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
            Text(text = subtitle, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
        }
        Switch(checked = checked, onCheckedChange = onCheckedChange)
    }
}

@Composable
fun LanguageToggle(
    currentLang: String,
    onLangChange: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Language, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = "অ্যাপের ভাষা", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
        }
        
        Row {
            FilterChip(
                selected = currentLang == "bn",
                onClick = { onLangChange("bn") },
                label = { Text("বাংলা") }
            )
            Spacer(modifier = Modifier.width(8.dp))
            FilterChip(
                selected = currentLang == "en",
                onClick = { onLangChange("en") },
                label = { Text("English") }
            )
        }
    }
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
@Composable
fun SettingsPreview() {
    InjaazhIslamicTheme {
        SettingsScreen()
    }
}
