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
import com.example.splitthebill.data.PartiesDao
import com.example.splitthebill.data.PartiesDataBase
import com.example.splitthebill.data.PartyUsersData
import com.example.splitthebill.data.PartiesData
import com.example.splitthebill.data.PartyWithUsers
import com.example.splitthebill.presenter.PartiesPresenter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class PartiesFragment : Fragment() {
    private lateinit var presenter: PartiesPresenter
    private lateinit var adapter: PartiesAdapter
    private lateinit var partiesDao: PartiesDao
    private val scope = CoroutineScope(Dispatchers.Main)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = inflater.inflate(R.layout.fragment_party_list, container, false)

        val partiesDB = PartiesDataBase.getDb(requireContext())
        partiesDao = partiesDB.partiesDao()

        presenter = PartiesPresenter(this, partiesDao)

        val recyclerView = binding.findViewById<RecyclerView>(R.id.recycler_parties)
        adapter = PartiesAdapter(mutableListOf())
        recyclerView.adapter = adapter

        presenter.loadParties()

        // Настраиваю кнопку для добавления новых коллективов
        val addButton: FloatingActionButton = binding.findViewById(R.id.party_add_button)
        addButton.setOnClickListener {
            presenter.addNewParty()
        }

        return binding
    }
    // Вызывается в Презентере для отображения коллективов
    fun showParties(parties: List<PartiesData>){
        adapter.updateParties(parties)
    }
}

