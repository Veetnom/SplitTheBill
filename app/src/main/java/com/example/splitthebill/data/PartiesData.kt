package com.example.splitthebill.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Embedded
import androidx.room.Ignore
import androidx.room.Relation

@Entity(tableName = "parties")
data class PartiesData(
    @PrimaryKey(autoGenerate = true)
    var partyId: Int? = null,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "friendsCount")
    var friendsCount: Int,
    @ColumnInfo(name = "color")
    var color: Int,
    @Ignore
    val children: List<PartyUsersData> = listOf()
) {
    constructor() : this(0, "", 0, 0, listOf())
}


@Entity(tableName = "partyUsers")
data class PartyUsersData(
    @PrimaryKey(autoGenerate = true)
    var partyUserId: Int? = null,
    @ColumnInfo(name = "name")
    val name: String,
) {
    constructor() : this(0, "")
}


@Entity (tableName = "friends")
data class FriendsData(
    @PrimaryKey(autoGenerate = true)
    var friendId: Int? = null,
    @ColumnInfo(name = "name")
    var name: String
){
    constructor() : this(0, "")
}

data class PartyWithUsers(
    @Embedded val party: PartiesData,
    @Relation(
        parentColumn = "partyId",
        entityColumn = "partyUserId"
    )
    val users: List<PartyUsersData>
)