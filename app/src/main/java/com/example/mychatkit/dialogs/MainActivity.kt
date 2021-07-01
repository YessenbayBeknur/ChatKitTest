package com.example.mychatkit.dialogs

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.mychatkit.NewDialogViewHolder
import com.example.mychatkit.R
import com.example.mychatkit.model.Dialog
import com.example.mychatkit.model.DialogsFixtures
import com.example.mychatkit.messages.MessageActivity
import com.squareup.picasso.Picasso
import com.stfalcon.chatkit.commons.ImageLoader
import com.stfalcon.chatkit.dialogs.DialogsList
import com.stfalcon.chatkit.dialogs.DialogsListAdapter

class MainActivity: AppCompatActivity(), DialogsListAdapter.OnDialogClickListener<Dialog>,
    DialogsListAdapter.OnDialogLongClickListener<Dialog> {

    private var dialogsList: DialogsList? = null
    lateinit var dialogsAdapter: DialogsListAdapter<Dialog>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dialogsList = findViewById(R.id.dialogsList)
        initAdapter()
    }

    override fun onDialogClick(dialog: Dialog?) {
        startActivity(
            Intent(
                applicationContext,
                MessageActivity::class.java
            )
        )
    }

    override fun onDialogLongClick(dialog: Dialog?) {

    }

    private fun initAdapter() {

        val imageLoader = ImageLoader { imageView: ImageView?, url: String?, payload: Any? ->
            Picasso.get().load(url).into(imageView)
        }

        dialogsAdapter = DialogsListAdapter(
            R.layout.item_dialog_view_holder,
            NewDialogViewHolder::class.java,
            imageLoader
        )
        dialogsAdapter.setItems(DialogsFixtures().getDialogs())
        dialogsAdapter.setOnDialogClickListener(this)
        dialogsAdapter.setOnDialogLongClickListener(this)
        dialogsList!!.setAdapter(dialogsAdapter)
    }


}