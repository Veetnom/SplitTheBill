package com.example.splitthebill

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.splitthebill.data.FriendsData
import com.google.android.material.card.MaterialCardView

class FriendsAdapter(
    private val dataSet: MutableList<FriendsData>,
    private val onUpdateFriend: (FriendsData) -> Unit,
    private val onFriendSelected: (FriendsData) -> Unit
) : RecyclerView.Adapter<FriendsAdapter.FriendViewHolder>() {

    private var selectedFriend: FriendsData? = null

    class FriendViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private val friendNameText: TextView = view.findViewById(R.id.friendName)
        private val friendNameEdit: EditText = view.findViewById(R.id.friendEditName)
        private val card: MaterialCardView = view.findViewById(R.id.friend_item)
        private val cardEdit: MaterialCardView = view.findViewById(R.id.friend_edit_item)
        private val editButton: ImageView = view.findViewById(R.id.editButton)
        private val confirmEditButton: ImageView = view.findViewById(R.id.confirmEditButton)

        fun bind(friend: FriendsData, adapter: FriendsAdapter, onUpdateFriend: (FriendsData) -> Unit) {
            // Отображение имени
            friendNameText.text = friend.name
            friendNameEdit.setText(friend.name)

            card.setOnClickListener {
                adapter.selectedFriend = friend // Сохраняем выбранного друга
                adapter.notifyDataSetChanged() // Перерисовываем адаптер
                adapter.onFriendSelected(friend) // Вызываем обратный вызов
            }
            // Показать поле редактирования при нажатии на кнопку "Редактировать"
            editButton.setOnClickListener {
                cardEdit.visibility = View.VISIBLE
            }

            // Подтвердить изменения
            confirmEditButton.setOnClickListener {
                val newName = friendNameEdit.text.toString()
                friend.name = newName // Обновляем локально

                // Обновляем друга через обратный вызов
                onUpdateFriend(friend)

                // Скрываем поле редактирования
                cardEdit.visibility = View.GONE

                // Обновляем отображение
                adapter.notifyItemChanged(adapterPosition)
            }
            if (adapter.selectedFriend == friend) {
                card.setCardBackgroundColor(ContextCompat.getColor(view.context, R.color.top)) // Выделяем цветом
            } else {
                card.setCardBackgroundColor(ContextCompat.getColor(view.context, R.color.white)) // Стандартный цвет
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.friend_item, parent, false)
        return FriendViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: FriendViewHolder, position: Int) {
        val friend = dataSet[position]
        holder.bind(friend, this, onUpdateFriend)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    fun updateFriends(newFriends: List<FriendsData>) {
        dataSet.clear()
        dataSet.addAll(newFriends)
        notifyDataSetChanged() // Уведомляем адаптер о том, что данные обновились
    }

    fun addFriend(newFriend: FriendsData) {
        dataSet.add(newFriend)
        notifyItemInserted(dataSet.size - 1) // Обновляем адаптер
    }
}
