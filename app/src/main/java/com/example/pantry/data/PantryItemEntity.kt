package com.example.pantry.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pantry_items")
data class PantryItemEntity(
    @PrimaryKey val id: String,
    val name: String,
    val category: String,
    val quantity: Int,
    val lowThreshold: Int = 1,
    val lowStock: Boolean = false
)