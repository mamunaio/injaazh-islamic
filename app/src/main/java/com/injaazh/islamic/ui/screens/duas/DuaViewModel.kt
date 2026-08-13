package com.injaazh.islamic.ui.screens.duas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.injaazh.islamic.data.local.entity.DuaCategoryEntity
import com.injaazh.islamic.data.local.entity.DuaEntity
import com.injaazh.islamic.domain.repository.DuaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DuaViewModel @Inject constructor(
    private val repository: DuaRepository
) : ViewModel() {

    private val _categories = MutableStateFlow<List<DuaCategoryEntity>>(emptyList())
    val categories: StateFlow<List<DuaCategoryEntity>> = _categories.asStateFlow()

    private val _duas = MutableStateFlow<List<DuaEntity>>(emptyList())
    val duas: StateFlow<List<DuaEntity>> = _duas.asStateFlow()

    init {
        viewModelScope.launch {
            repository.populateDataIfNeeded()
            loadCategories()
        }
    }

    private fun loadCategories() {
        viewModelScope.launch {
            repository.getAllCategories().collect {
                _categories.value = it
            }
        }
    }

    fun loadDuasByCategory(categoryId: Int) {
        viewModelScope.launch {
            repository.getDuasByCategory(categoryId).collect {
                _duas.value = it
            }
        }
    }

    fun toggleFavorite(dua: DuaEntity) {
        viewModelScope.launch {
            repository.updateDua(dua.copy(isFavorite = !dua.isFavorite))
        }
    }
}
