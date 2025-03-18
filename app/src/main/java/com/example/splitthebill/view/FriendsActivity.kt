package com.example.splitthebill.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.splitthebill.FriendsAdapter
import com.example.splitthebill.R
import com.example.splitthebill.data.FriendsData
import com.example.splitthebill.data.PartiesDataBase
import com.example.splitthebill.data.PartyUsersData
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FriendsActivity : AppCompatActivity() {
    private lateinit var friendsDB: PartiesDataBase
    private lateinit var adapter: FriendsAdapter
    private var selectedFriend: FriendsData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        window.statusBarColor = ContextCompat.getColor(this, R.color.top)
        setContentView(R.layout.activity_friends_list)

        // Применяем отступы для системных панелей
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Инициализация базы данных
        friendsDB = PartiesDataBase.getDb(this)

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_friends)
        val addFriendToPartyButton: FloatingActionButton = findViewById(R.id.friend_add_to_party_button)
        addFriendToPartyButton.visibility = View.GONE
        adapter = FriendsAdapter(mutableListOf(), { friend ->
            // Обновление друга в базе данных
            lifecycleScope.launch {
                friendsDB.partiesDao().updateFriend(friend)
            }
        }, { friend ->
            // Отображаем кнопку добавления друга
            selectedFriend = friend
            addFriendToPartyButton.visibility = View.VISIBLE
        })
        recyclerView.adapter = adapter

        // Загружаем данные из базы данных
        loadFriends()

        val addFriendButton: FloatingActionButton = findViewById(R.id.friend_add_button)
        addFriendButton.setOnClickListener{
            addNewFriend()
        }

        addFriendToPartyButton.setOnClickListener {
            Log.d("S", selectedFriend.toString())
            selectedFriend?.let { friend ->
                addFriendToParty(friend)
            }
        }
    }

    // Функция для загрузки списка друзей из базы данных
    private fun loadFriends() {
        lifecycleScope.launch {
            // Получаем список всех друзей
            val friendsFromDb = friendsDB.partiesDao().getAllFriends()

            // Обновляем адаптер в главном потоке
            withContext(Dispatchers.Main) {
                adapter.updateFriends(friendsFromDb) // Обновляем адаптер с новыми данными
            }
        }
    }
    private fun addNewFriend() {
        lifecycleScope.launch {
            val newFriend = FriendsData(
                name = "Новый друг"
            )

            // Добавление новой группы в базу данных
            val newFriendId = friendsDB.partiesDao().insertFriend(newFriend)

            // Обновляем адаптер в главном потоке
            withContext(Dispatchers.Main) {
                newFriend.friendId = newFriendId.toInt()
                adapter.addFriend(newFriend)
            }
        }
    }
    private fun addFriendToParty(friend: FriendsData) {
        val partyId = intent.getIntExtra("partyId", -1)
        if (partyId != -1) {
            lifecycleScope.launch {
                val party = friendsDB.partiesDao().getPartyById(partyId).firstOrNull()
                if (party != null) {
                    // Добавляем друга в список пользователей вечеринки
                    friendsDB.partiesDao().insertPartyUser(
                        PartyUsersData(name = friend.name)
                    )

                    // Обновляем количество друзей
                    val updatedParty = party.copy(friendsCount = party.friendsCount + 1)
                    friendsDB.partiesDao().updateParty(updatedParty)

                    // Обновляем интерфейс
                    withContext(Dispatchers.Main) {
                        loadFriends() // Перезагружаем список друзей
                    }
                } else {
                    Log.e("FriendsListActivity", "Party with ID $partyId not found")
                }
            }
        } else {
            Log.e("FriendsListActivity", "Invalid partyId: $partyId")
        }
    }

}

