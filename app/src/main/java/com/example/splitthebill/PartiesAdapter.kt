package com.example.splitthebill

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.splitthebill.data.PartiesData
import com.example.splitthebill.data.PartyWithUsers
import com.example.splitthebill.view.FriendsListActivity
import com.example.splitthebill.view.MainActivity
import com.google.android.material.card.MaterialCardView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class PartiesAdapter(private val dataSet: MutableList<PartiesData>) : RecyclerView.Adapter<PartiesAdapter.PartiesViewHolder>() {

    class PartiesViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private val partyName: TextView = view.findViewById(R.id.partyName)
        private val friendsCount: TextView = view.findViewById(R.id.friendsCount)
        private val childRecyclerView: RecyclerView = view.findViewById(R.id.childList)
        private val childLinearLayout: LinearLayout = view.findViewById(R.id.linearLayout_partyUsers)
        private val card: MaterialCardView = view.findViewById(R.id.party_item)
        private val addPartyUsersButton: ImageView = view.findViewById(R.id.party_user_add_button)

        fun bind(parentItem: PartiesData) {
            partyName.text = parentItem.name
            friendsCount.text = parentItem.friendsCount.toString()

            // Настройка адаптера для дочернего RecyclerView
            childRecyclerView.layoutManager = LinearLayoutManager(itemView.context)
            childRecyclerView.adapter = ExpandPartiesAdapter(parentItem.children)

            // Раскрытие/сворачивание при клике на карточку
            card.setOnClickListener {
                childLinearLayout.visibility = if (childLinearLayout.visibility == View.GONE) View.VISIBLE else View.GONE
            }

            // Открытие списка друзей для добавления
            addPartyUsersButton.setOnClickListener {
                val intent = Intent(itemView.context, FriendsListActivity::class.java)
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PartiesViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.party_item, parent, false)
        return PartiesViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: PartiesViewHolder, position: Int) {
        val party = dataSet[position]
        holder.bind(party)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    fun updateParties(newParties: List<PartiesData>) {
        dataSet.clear()
        dataSet.addAll(newParties)
        notifyDataSetChanged() // Уведомляем адаптер о том, что данные обновились
    }

    fun addParty(newParty: PartyWithUsers) {
        // Добавляем новую партию с пользователями
        dataSet.add(newParty.party)
        notifyItemInserted(dataSet.size - 1) // Уведомляем адаптер о новом элементе
    }
}

