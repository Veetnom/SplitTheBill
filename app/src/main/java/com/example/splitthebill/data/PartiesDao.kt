package com.example.splitthebill.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update

@Dao
interface PartiesDao {
    @Insert
    suspend fun insertParty(party: PartiesData): Long

    @Insert
    suspend fun insertPartyUser(partyUser: PartyUsersData)

    @Insert
    suspend fun insertFriend(friend: FriendsData): Long

    @Query("SELECT * FROM parties")
    suspend fun getAllParties(): List<PartiesData>

    @Query("SELECT * FROM parties WHERE partyId = :partyId")
    suspend fun getPartyById(partyId: Int): List<PartiesData>

    @Query("SELECT * FROM friends")
    suspend fun getAllFriends(): List<FriendsData>

    @Query("SELECT * FROM partyUsers WHERE partyUserId = :partyUsersId")
    suspend fun getPartyUsersByListId(partyUsersId: Int): List<PartyUsersData>

    @Update
    suspend fun updateParty(party: PartiesData)

    @Update
    suspend fun updateUser(partyUser: PartyUsersData)

    @Update
    suspend fun updateFriend(friend: FriendsData)

    @Transaction
    @Query("SELECT * FROM parties")
    suspend fun getPartiesWithUsers(): List<PartyWithUsers>
}
