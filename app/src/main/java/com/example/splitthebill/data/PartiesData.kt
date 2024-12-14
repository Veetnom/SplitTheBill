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
    var partyId: Int? = null,  // Значение этого поля будет автоматически сгенерировано Room
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "friendsCount")
    var friendsCount: Int,
    @ColumnInfo(name = "color")
    var color: Int,
    @Ignore
    val children: List<PartyUsersData> = listOf()  // Этот список не будет сохраняться в базе данных
) {
    // Публичный пустой конструктор для Room
    constructor() : this(0, "", 0, 0, listOf())
}


@Entity(tableName = "partyUsers")
data class PartyUsersData(
    @PrimaryKey(autoGenerate = true)
    var partyUserId: Int? = null,
    @ColumnInfo(name = "name")
    val name: String,
) {
    // Публичный пустой конструктор необходим для Room
    constructor() : this(0, "") // Пустой конструктор
}


@Entity (tableName = "friends")
data class FriendsData(
    @PrimaryKey(autoGenerate = true)
    var friendId: Int? = null,
    @ColumnInfo(name = "name")
    var name: String
){
    // Публичный пустой конструктор необходим для Room
    constructor() : this(0, "") // Пустой конструктор
}

data class PartyWithUsers(
    @Embedded val party: PartiesData, // Данные группы
    @Relation(
        parentColumn = "partyId", // Столбец в таблице groups
        entityColumn = "partyUserId" // Столбец в таблице users
    )
    val users: List<PartyUsersData> // Список пользователей, связанных с этой группой
)