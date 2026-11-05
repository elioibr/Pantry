package com.example.pantry.screens

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController

@Composable
fun PantryApp() {
    val navController = rememberNavController()
    AppNavHost(navController)
}