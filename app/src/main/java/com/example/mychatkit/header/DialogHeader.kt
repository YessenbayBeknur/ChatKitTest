package com.example.mychatkit.header

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mychatkit.R
import com.example.mychatkit.messages.MessageActivity
import com.example.mychatkit.model.Dialog
import com.squareup.picasso.Picasso

class DialogHeader: AppCompatActivity() {

    lateinit var dialog: Dialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_header_dialog)

        dialog = intent.getParcelableExtra("dialog")!!

        findViewById<TextView>(R.id.dialogTitle).text = dialog.dialogName
        findViewById<TextView>(R.id.membersCount).text = dialog.users?.size.toString()
        Picasso.get().load(dialog.dialogPhoto).into(findViewById<ImageView>(R.id.dialogInfoAvatar))

        val recyclerView = findViewById<RecyclerView>(R.id.memberList)
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.adapter = dialog.users?.let { DialogUsersAdapter(it, applicationContext) }
        recyclerView.adapter?.notifyDataSetChanged()

        findViewById<ImageButton>(R.id.dialogInfoBackBtn).setOnClickListener {
            Intent(
                applicationContext,
                MessageActivity::class.java
            ).apply { putExtra("dialog", dialog) }
        }



    }

}