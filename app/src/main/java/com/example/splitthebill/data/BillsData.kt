package com.example.splitthebill.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "productTable")
data class ProductData(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    var name: String = "",
    var count: String = "0",
    var price: String = "0.00"
)
{
    constructor() : this(0, "", "0", "0")
}