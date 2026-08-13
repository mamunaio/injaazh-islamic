package com.injaazh.islamic.ui.screens.education

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AsmaulHusna(
    val id: Int,
    val nameAr: String,
    val nameEn: String,
    val meaningBn: String,
    val descriptionBn: String
)

@HiltViewModel
class EducationViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val gson: Gson
) : ViewModel() {

    private val _asmaulHusna = MutableStateFlow<List<AsmaulHusna>>(emptyList())
    val asmaulHusna: StateFlow<List<AsmaulHusna>> = _asmaulHusna.asStateFlow()

    init {
        loadAsmaulHusna()
    }

    private fun loadAsmaulHusna() {
        viewModelScope.launch {
            try {
                val jsonString = context.assets.open("asmaul_husna.json").bufferedReader().use { it.readText() }
                val listType = object : TypeToken<List<AsmaulHusna>>() {}.type
                val list: List<AsmaulHusna> = gson.fromJson(jsonString, listType)
                _asmaulHusna.value = list
            } catch (e: Exception) {
                _asmaulHusna.value = emptyList()
            }
        }
    }
}
