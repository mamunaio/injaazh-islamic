package com.injaazh.islamic.ui.screens.tasbih

import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.injaazh.islamic.R
import com.injaazh.islamic.ui.theme.InjaazhIslamicTheme

@Composable
fun TasbihScreen(
    viewModel: TasbihViewModel = hiltViewModel()
) {
    val count by viewModel.count.collectAsState()
    val target by viewModel.target.collectAsState()
    val haptic = LocalHapticFeedback.current

    TasbihScreenContent(
        count = count,
        target = target,
        onIncrement = {
            viewModel.incrementCount()
            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
        },
        onReset = { viewModel.resetCount() },
        onSetTarget = { viewModel.setTarget(it) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasbihScreenContent(
    count: Int,
    target: Int,
    onIncrement: () -> Unit,
    onReset: () -> Unit,
    onSetTarget: (Int) -> Unit
) {
    val progress by animateFloatAsState(
        targetValue = (count % target).toFloat() / target,
        animationSpec = tween(500), label = "TasbihProgress"
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "ডিজিটাল তাসবিহ", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White
                )
            )
        }
    ) { padding ->
        Column(
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
                )
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) { onIncrement() },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(contentAlignment = Alignment.Center) {
                // Outer Glow/Shadow Circle
                Box(
                    modifier = Modifier
                        .size(320.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.05f))
                )

                // Background Ring
                CircularProgressIndicator(
                    progress = { 1f },
                    modifier = Modifier.size(280.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    strokeWidth = 16.dp,
                    strokeCap = StrokeCap.Round,
                )

                // Active Progress Ring
                CircularProgressIndicator(
                    progress = { progress },
                    modifier = Modifier.size(280.dp),
                    color = MaterialTheme.colorScheme.primary,
                    strokeWidth = 16.dp,
                    strokeCap = StrokeCap.Round,
                )

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    AnimatedContent(
                        targetState = count,
                        transitionSpec = {
                            (slideInVertically { height -> height } + fadeIn()).togetherWith(
                                slideOutVertically { height -> -height } + fadeOut()
                            )
                        }, label = "CountAnimation"
                    ) { targetCount ->
                        Text(
                            text = "$targetCount",
                            fontSize = 80.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    
                    Surface(
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text(
                            text = "লক্ষ্য: $target",
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(60.dp))

            // Control Buttons
            Row(
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Reset Button
                FilledTonalIconButton(
                    onClick = onReset,
                    modifier = Modifier.size(56.dp)
                ) {
                    Icon(Icons.Default.Refresh, contentDescription = "Reset")
                }

                // Target Selector
                TargetChip(label = "৩৩", isSelected = target == 33) { onSetTarget(33) }
                TargetChip(label = "১০০", isSelected = target == 100) { onSetTarget(100) }
                TargetChip(label = "∞", isSelected = target == 999999) { onSetTarget(999999) }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Text(
                text = "স্ক্রিনের যেকোনো জায়গায় ট্যাপ করুন",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
            )
        }
    }
}

@Composable
fun TargetChip(label: String, isSelected: Boolean, onClick: () -> Unit) {
    FilterChip(
        selected = isSelected,
        onClick = onClick,
        label = { Text(label, fontWeight = FontWeight.Bold) },
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = MaterialTheme.colorScheme.primary,
            selectedLabelColor = Color.White
        ),
        shape = RoundedCornerShape(12.dp)
    )
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
@Composable
fun TasbihPreview() {
    InjaazhIslamicTheme {
        TasbihScreenContent(
            count = 33,
            target = 100,
            onIncrement = {},
            onReset = {},
            onSetTarget = {}
        )
    }
}
