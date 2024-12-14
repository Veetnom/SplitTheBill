package com.example.splitthebill.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.splitthebill.PartiesAdapter
import com.example.splitthebill.R
import com.example.splitthebill.data.PartiesDataBase
import com.example.splitthebill.data.PartyUsersData
import com.example.splitthebill.data.PartiesData
import com.example.splitthebill.data.PartyWithUsers
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class PartiesFragment : Fragment() {
    private lateinit var partiesDB: PartiesDataBase
    private lateinit var adapter: PartiesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = inflater.inflate(R.layout.fragment_party_list, container, false)

        // Инициализация базы данных
        partiesDB = PartiesDataBase.getDb(requireContext())

        val recyclerView = binding.findViewById<RecyclerView>(R.id.recycler_parties)
        adapter = PartiesAdapter(mutableListOf()) // Инициализация адаптера с пустым списком
        recyclerView.adapter = adapter

        // Загружаем данные из базы данных
        loadParties()

        // Добавление новой вечеринки
        val addButton: FloatingActionButton = binding.findViewById(R.id.party_add_button)
        addButton.setOnClickListener {
            addNewParty() // Добавляем новую вечеринку
        }

        return binding
    }

    // Загружаем список вечеринок из базы данных
    private fun loadParties() {
        lifecycleScope.launch {
            val partiesFromDb = partiesDB.partiesDao().getPartiesWithUsers() // Получаем данные о вечеринках с пользователями

            // Обновляем адаптер в главном потоке
            withContext(Dispatchers.Main) {
                val partiesList = mutableListOf<PartiesData>()
                partiesFromDb.forEach {
                    partiesList.add(it.party) // Добавляем группу в список
                }
                adapter.updateParties(partiesList) // Обновляем адаптер
            }
        }
    }

    // Функция для добавления новой вечеринки
    private fun addNewParty() {
        lifecycleScope.launch {
            val newParty = PartiesData(
                // ID будет автоматически генерироваться Room
                name = "Новая группа",
                friendsCount = 0,
                color = R.color.test
            )

            // Добавление новой группы в базу данных
            val newPartyId = partiesDB.partiesDao().insertParty(newParty)

            // Обновляем адаптер в главном потоке
            withContext(Dispatchers.Main) {
                val updatedParty = PartyWithUsers(
                    party = newParty.copy(partyId = newPartyId.toInt()),
                    users = listOf()
                )
                adapter.addParty(updatedParty)
            }
        }
    }
}

