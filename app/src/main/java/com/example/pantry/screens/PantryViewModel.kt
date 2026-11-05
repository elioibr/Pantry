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
import java.util.UUID

class PantryViewModel(
    private val repository: PantryRepository
) : ViewModel() {

    val uiState: StateFlow<ItemsUiState> = repository.items
        .map<List<PantryItemEntity>, ItemsUiState> { ItemsUiState.Content(it) }
        .catch { emit(ItemsUiState.Error(it.message ?: "Unknown error")) }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            ItemsUiState.Loading
        )

    fun addItem(name: String, category: String, quantity: Int, lowThreshold: Int) =
        viewModelScope.launch {
            repository.save(
                PantryItemEntity(
                    id = UUID.randomUUID().toString(),
                    name = name,
                    category = category,
                    quantity = quantity,
                    lowThreshold = lowThreshold
                )
            )
        }

    fun changeQuantity(item: PantryItemEntity, delta: Int) =
        viewModelScope.launch {
            repository.setQuantity(item.id, (item.quantity + delta).coerceAtLeast(0))
        }

    fun toggleLowStock(item: PantryItemEntity) =
        viewModelScope.launch {
            repository.setLowStock(item.id, !item.lowStock)
        }

    fun delete(item: PantryItemEntity) =
        viewModelScope.launch { repository.delete(item) }
}