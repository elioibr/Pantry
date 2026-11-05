package com.example.pantry.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pantry.data.PantryItemEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingScreen(
    state: ItemsUiState,
    onMarkBought: (PantryItemEntity) -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Shopping list") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        when (state) {
            ItemsUiState.Loading -> CenteredMessage("Loading…", padding)
            is ItemsUiState.Error -> CenteredMessage(state.message, padding)
            is ItemsUiState.Content ->
                if (state.items.isEmpty()) {
                    CenteredMessage("Nothing to buy", padding)
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(state.items, key = { it.id }) { item ->
                            ShoppingRow(item = item, onMarkBought = onMarkBought)
                        }
                    }
                }
        }
    }
}

@Composable
fun ShoppingRow(
    item: PantryItemEntity,
    onMarkBought: (PantryItemEntity) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(item.name)
            Text("Left: ${item.quantity} • ${item.category}")
        }
        TextButton(onClick = { onMarkBought(item) }) { Text("Bought") }
    }
}