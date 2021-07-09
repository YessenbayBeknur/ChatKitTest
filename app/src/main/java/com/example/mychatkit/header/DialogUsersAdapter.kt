package com.example.mychatkit.header

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mychatkit.R
import com.example.mychatkit.model.User
import com.squareup.picasso.Picasso

class DialogUsersAdapter(private val arrayList: ArrayList<User>,
                         val context: Context): RecyclerView.Adapter<DialogUsersAdapter.UsersViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): UsersViewHolder {
        val root = LayoutInflater.from(parent.context).inflate(R.layout.item_dialog_header_users, parent,false)

        return UsersViewHolder(root)
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        holder.bind(arrayList[position])
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    class UsersViewHolder(private val lists: View) : RecyclerView.ViewHolder(lists) {
        fun bind(user: User){
            lists.findViewById<TextView>(R.id.userName).text = user.name
            lists.findViewById<TextView>(R.id.userStatus).text = user.isOnline().toString()
            Picasso.get().load(user.avatar).into(lists.findViewById<ImageView>(R.id.userAvatar))
        }

    }

}