package com.example.splitthebill

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.splitthebill.data.PartyUsersData

class ExpandPartiesAdapter(private val dataSet: List<PartyUsersData>) : RecyclerView.Adapter<ExpandPartiesAdapter.ChildViewHolder>() {

    class ChildViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private val childName: TextView = view.findViewById(R.id.partyUserName)

        fun bind(childItem: PartyUsersData) {
            childName.text = childItem.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.party_user_item, parent, false)
        return ChildViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ChildViewHolder, position: Int) {
        val user = dataSet[position]
        holder.bind(user)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}
