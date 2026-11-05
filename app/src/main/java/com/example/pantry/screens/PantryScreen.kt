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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
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
fun PantryScreen(
    state: ItemsUiState,
    onAddItem: () -> Unit,
    onOpenShopping: () -> Unit,
    onQuantityChange: (PantryItemEntity, Int) -> Unit,
    onToggleLowStock: (PantryItemEntity) -> Unit,
    onDelete: (PantryItemEntity) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pantry") },
                actions = {
                    TextButton(onClick = onOpenShopping) { Text("Shopping list") }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddItem) {
                Icon(Icons.Default.Add, contentDescription = "Add item")
            }
        }
    ) { padding ->
        when (state) {
            ItemsUiState.Loading -> CenteredMessage("Loading…", padding)
            is ItemsUiState.Error -> CenteredMessage(state.message, padding)
            is ItemsUiState.Content ->
                if (state.items.isEmpty()) {
                    CenteredMessage("No items yet. Tap + to add one.", padding)
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(state.items, key = { it.id }) { item ->
                            PantryItemRow(
                                item = item,
                                onQuantityChange = onQuantityChange,
                                onToggleLowStock = onToggleLowStock,
                                onDelete = onDelete
                            )
                        }
                    }
                }
        }
    }
}

@Composable
fun PantryItemRow(
    item: PantryItemEntity,
    onQuantityChange: (PantryItemEntity, Int) -> Unit,
    onToggleLowStock: (PantryItemEntity) -> Unit,
    onDelete: (PantryItemEntity) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = item.lowStock,
            onCheckedChange = { onToggleLowStock(item) }
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(item.name)
            Text(item.category + if (item.lowStock) " • Low stock" else "")
        }
        IconButton(onClick = { onQuantityChange(item, -1) }) { Text("−") }
        Text("${item.quantity}")
        IconButton(onClick = { onQuantityChange(item, 1) }) { Text("+") }
        IconButton(onClick = { onDelete(item) }) {
            Icon(Icons.Default.Delete, contentDescription = "Delete")
        }
    }
}