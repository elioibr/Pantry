package com.example.pantry.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface PantryDao {
    @Query("SELECT * FROM pantry_items ORDER BY name")
    fun observeAll(): Flow<List<PantryItemEntity>>

    @Query("SELECT * FROM pantry_items WHERE lowStock = 1 ORDER BY name")
    fun observeShoppingList(): Flow<List<PantryItemEntity>>

    @Query("SELECT * FROM pantry_items WHERE lowStock = 1")
    suspend fun getShoppingList(): List<PantryItemEntity>

    @Upsert
    suspend fun upsert(item: PantryItemEntity)

    @Query(
        "UPDATE pantry_items SET quantity = :quantity, " +
                "lowStock = CASE WHEN :quantity <= lowThreshold THEN 1 ELSE lowStock END " +
                "WHERE id = :id"
    )
    suspend fun updateQuantity(id: String, quantity: Int)

    @Query("UPDATE pantry_items SET lowStock = :lowStock WHERE id = :id")
    suspend fun updateLowStock(id: String, lowStock: Boolean)

    @Query(
        "UPDATE pantry_items SET lowStock = 0, " +
                "quantity = MAX(quantity, lowThreshold + 1) WHERE id = :id"
    )
    suspend fun markBought(id: String)

    @Delete
    suspend fun delete(item: PantryItemEntity)
}