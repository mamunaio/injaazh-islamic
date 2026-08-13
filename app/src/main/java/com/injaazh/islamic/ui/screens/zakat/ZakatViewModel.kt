package com.injaazh.islamic.ui.screens.zakat

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class ZakatUiState(
    val cash: String = "",
    val gold: String = "",
    val silver: String = "",
    val investments: String = "",
    val businessGoods: String = "",
    val debts: String = "",
    val totalAssets: Double = 0.0,
    val totalLiabilities: Double = 0.0,
    val netWealth: Double = 0.0,
    val zakatPayable: Double = 0.0,
    val isEligible: Boolean = false
)

@HiltViewModel
class ZakatViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(ZakatUiState())
    val uiState: StateFlow<ZakatUiState> = _uiState.asStateFlow()

    // Default Nisab value (approximate price of 52.5 tola silver or 7.5 tola gold)
    // For simplicity, let's assume a threshold in currency. 
    // In a real app, this would be dynamic via API or user input.
    private val nisabThreshold = 80000.0 

    fun onCashChange(value: String) {
        _uiState.update { it.copy(cash = value) }
        calculateZakat()
    }

    fun onGoldChange(value: String) {
        _uiState.update { it.copy(gold = value) }
        calculateZakat()
    }

    fun onSilverChange(value: String) {
        _uiState.update { it.copy(silver = value) }
        calculateZakat()
    }

    fun onInvestmentsChange(value: String) {
        _uiState.update { it.copy(investments = value) }
        calculateZakat()
    }

    fun onBusinessGoodsChange(value: String) {
        _uiState.update { it.copy(businessGoods = value) }
        calculateZakat()
    }

    fun onDebtsChange(value: String) {
        _uiState.update { it.copy(debts = value) }
        calculateZakat()
    }

    private fun calculateZakat() {
        _uiState.update { state ->
            val cashVal = state.cash.toDoubleOrNull() ?: 0.0
            val goldVal = state.gold.toDoubleOrNull() ?: 0.0
            val silverVal = state.silver.toDoubleOrNull() ?: 0.0
            val investVal = state.investments.toDoubleOrNull() ?: 0.0
            val goodsVal = state.businessGoods.toDoubleOrNull() ?: 0.0
            val debtsVal = state.debts.toDoubleOrNull() ?: 0.0

            val totalAssets = cashVal + goldVal + silverVal + investVal + goodsVal
            val netWealth = totalAssets - debtsVal
            val isEligible = netWealth >= nisabThreshold
            val zakatPayable = if (isEligible) netWealth * 0.025 else 0.0

            state.copy(
                totalAssets = totalAssets,
                totalLiabilities = debtsVal,
                netWealth = netWealth,
                zakatPayable = zakatPayable,
                isEligible = isEligible
            )
        }
    }
}
