package com.example.pantry.data

import kotlinx.coroutines.flow.Flow

class PantryRepository(private val dao: PantryDao) {
    val items: Flow<List<PantryItemEntity>> = dao.observeAll()
    val shoppingList: Flow<List<PantryItemEntity>> = dao.observeShoppingList()

    suspend fun getShoppingList(): List<PantryItemEntity> = dao.getShoppingList()

    suspend fun save(item: PantryItemEntity) {

        val lowStock = item.lowStock || item.quantity <= item.lowThreshold
        dao.upsert(item.copy(lowStock = lowStock))
    }

    suspend fun setQuantity(id: String, quantity: Int) =
        dao.updateQuantity(id, quantity)

    suspend fun setLowStock(id: String, lowStock: Boolean) =
        dao.updateLowStock(id, lowStock)

    suspend fun markBought(id: String) = dao.markBought(id)

    suspend fun delete(item: PantryItemEntity) = dao.delete(item)
}