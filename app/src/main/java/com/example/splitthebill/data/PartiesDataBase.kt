package com.example.splitthebill.data

import android.content.Context
import androidx.room.Room
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [PartiesData::class, PartyUsersData::class, FriendsData::class], version = 1)
abstract class PartiesDataBase : RoomDatabase() {
    abstract fun partiesDao(): PartiesDao
    companion object{
        fun getDb(context: Context): PartiesDataBase{
            return Room.databaseBuilder(
                context.applicationContext,
                PartiesDataBase::class.java, "party.db"
            ).build()
        }
    }
}