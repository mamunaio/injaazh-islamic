package com.injaazh.islamic.ui.screens.zakat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.injaazh.islamic.R
import com.injaazh.islamic.ui.theme.InjaazhIslamicTheme

@Composable
fun ZakatScreen(
    viewModel: ZakatViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    ZakatScreenContent(uiState, viewModel)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ZakatScreenContent(
    state: ZakatUiState,
    viewModel: ZakatViewModel? = null
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = stringResource(R.string.zakat_title), fontWeight = FontWeight.Bold) },
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
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Premium Result Card
            ZakatResultCard(state)

            Text(
                text = stringResource(R.string.enter_assets),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            // Asset Inputs
            AssetInputItem(
                label = stringResource(R.string.cash_bank),
                value = state.cash,
                onValueChange = { viewModel?.onCashChange(it) }
            )
            AssetInputItem(
                label = stringResource(R.string.gold_value),
                value = state.gold,
                onValueChange = { viewModel?.onGoldChange(it) }
            )
            AssetInputItem(
                label = stringResource(R.string.silver_value),
                value = state.silver,
                onValueChange = { viewModel?.onSilverChange(it) }
            )
            AssetInputItem(
                label = stringResource(R.string.investments),
                value = state.investments,
                onValueChange = { viewModel?.onInvestmentsChange(it) }
            )
            AssetInputItem(
                label = stringResource(R.string.business_goods),
                value = state.businessGoods,
                onValueChange = { viewModel?.onBusinessGoodsChange(it) }
            )

            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = stringResource(R.string.liabilities),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color.Red.copy(alpha = 0.7f)
            )

            AssetInputItem(
                label = stringResource(R.string.debts_to_pay),
                value = state.debts,
                onValueChange = { viewModel?.onDebtsChange(it) },
                isLiability = true
            )
            
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun ZakatResultCard(state: ZakatUiState) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
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
                    text = stringResource(R.string.total_zakat_payable),
                    color = Color.White.copy(alpha = 0.8f),
                    style = MaterialTheme.typography.labelLarge
                )
                Text(
                    text = "৳ ${String.format("%.2f", state.zakatPayable)}",
                    color = Color.White,
                    style = MaterialTheme.typography.displayMedium,
                    fontWeight = FontWeight.ExtraBold
                )
                
                if (!state.isEligible && state.totalAssets > 0) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Surface(
                        color = Color.White.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.not_eligible_zakat),
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.White
                        )
                    }
                }
                
                HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp), color = Color.White.copy(alpha = 0.2f))
                
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    ResultSummaryItem(stringResource(R.string.total_assets), state.totalAssets)
                    ResultSummaryItem(stringResource(R.string.net_wealth), state.netWealth)
                }
            }
        }
    }
}

@Composable
fun ResultSummaryItem(label: String, amount: Double) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = label, color = Color.White.copy(alpha = 0.7f), style = MaterialTheme.typography.labelSmall)
        Text(text = "৳ ${amount.toInt()}", color = Color.White, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
fun AssetInputItem(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    isLiability: Boolean = false
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(16.dp),
        tonalElevation = 1.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelLarge,
                color = if (isLiability) Color.Red.copy(alpha = 0.6f) else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("0.00") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = if (isLiability) Color.Red else MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant
                ),
                shape = RoundedCornerShape(12.dp)
            )
        }
    }
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
@Composable
fun ZakatPreview() {
    InjaazhIslamicTheme {
        ZakatScreenContent(
            state = ZakatUiState(
                totalAssets = 150000.0,
                netWealth = 120000.0,
                zakatPayable = 3000.0,
                isEligible = true
            )
        )
    }
}
