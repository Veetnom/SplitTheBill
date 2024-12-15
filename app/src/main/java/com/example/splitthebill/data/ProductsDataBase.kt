package com.example.splitthebill.data

import android.content.Context
import androidx.room.Room
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ProductData::class], version = 1)
abstract class ProductsDataBase : RoomDatabase() {
    abstract fun prouctsDao(): ProductsDao
    companion object{
        fun getDb(context: Context): ProductsDataBase{
            return Room.databaseBuilder(
                context.applicationContext,
                ProductsDataBase::class.java, "products.db"
            ).build()
        }
    }
}