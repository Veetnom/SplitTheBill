package com.example.splitthebill.presenter

import androidx.lifecycle.lifecycleScope
import com.example.splitthebill.data.PartiesData
import com.example.splitthebill.fragments.PartiesFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.lifecycle.lifecycleScope
import com.example.splitthebill.R
import com.example.splitthebill.data.PartiesDao
import com.example.splitthebill.data.PartiesDataBase
import com.example.splitthebill.data.PartyWithUsers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class PartiesPresenter (private val view: PartiesFragment, private val partiesDao: PartiesDao) {
    private val scope = CoroutineScope(Dispatchers.Main)
    fun loadParties() {
        scope.launch {
            val parties = partiesDao.getAllParties()
            view.showParties(parties)
        }
    }
    fun addNewParty() {
        scope.launch {
            val newParty = PartiesData(
                name = "Новая группа",
                friendsCount = 0,
                color = R.color.test
            )
            partiesDao.insertParty(newParty)
        }
    }
}
