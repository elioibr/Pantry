package com.example.pantry.screens

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.pantry.PantryApplication

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer { PantryViewModel(pantryApplication().repository) }
        initializer { ShoppingViewModel(pantryApplication().repository) }
    }
}

private fun CreationExtras.pantryApplication(): PantryApplication =
    this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as PantryApplication