package com.example.pantry.screens

import com.example.pantry.data.PantryItemEntity

sealed interface ItemsUiState {
    data object Loading : ItemsUiState
    data class Content(val items: List<PantryItemEntity>) : ItemsUiState
    data class Error(val message: String) : ItemsUiState
}