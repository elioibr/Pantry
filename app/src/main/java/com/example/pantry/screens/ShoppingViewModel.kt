package com.example.pantry.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pantry.data.PantryItemEntity
import com.example.pantry.data.PantryRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ShoppingViewModel(
    private val repository: PantryRepository
) : ViewModel() {

    val uiState: StateFlow<ItemsUiState> = repository.shoppingList
        .map<List<PantryItemEntity>, ItemsUiState> { ItemsUiState.Content(it) }
        .catch { emit(ItemsUiState.Error(it.message ?: "Unknown Error")) }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            ItemsUiState.Loading
        )

    fun markBought(item: PantryItemEntity) =
        viewModelScope.launch { repository.markBought(item.id) }
}