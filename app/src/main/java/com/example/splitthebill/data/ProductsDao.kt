package com.example.splitthebill.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ProductsDao {
    @Insert
    suspend fun insertProduct(product: ProductData)

    @Query("SELECT * FROM productTable")
    suspend fun getAllProducts(): List<ProductData>

    @Delete
    suspend fun deleteProduct(product: ProductData)
}
