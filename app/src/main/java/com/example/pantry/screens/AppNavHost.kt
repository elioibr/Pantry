package com.example.pantry.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun AppNavHost(navController: NavHostController) {

    val pantryViewModel: PantryViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val shoppingViewModel: ShoppingViewModel = viewModel(factory = AppViewModelProvider.Factory)

    NavHost(
        navController = navController,
        startDestination = "pantry"
    ) {
        composable("pantry") {
            val state by pantryViewModel.uiState.collectAsStateWithLifecycle()
            PantryScreen(
                state = state,
                onAddItem = { navController.navigate("add") },
                onOpenShopping = { navController.navigate("shopping") },
                onQuantityChange = { item, delta -> pantryViewModel.changeQuantity(item, delta) },
                onToggleLowStock = { item -> pantryViewModel.toggleLowStock(item) },
                onDelete = { item -> pantryViewModel.delete(item) }
            )
        }
        composable("shopping") {
            val state by shoppingViewModel.uiState.collectAsStateWithLifecycle()
            ShoppingScreen(
                state = state,
                onMarkBought = { item -> shoppingViewModel.markBought(item) },
                onBack = { navController.popBackStack() }
            )
        }
        composable("add") {
            AddItemScreen(
                onSave = { name, category, quantity, lowThreshold ->
                    pantryViewModel.addItem(name, category, quantity, lowThreshold)
                    navController.popBackStack()
                },
                onBack = { navController.popBackStack() }
            )
        }
    }
}