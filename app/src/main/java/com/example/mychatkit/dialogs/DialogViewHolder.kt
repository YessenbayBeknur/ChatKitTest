package com.example.mychatkit

import android.view.View
import com.example.mychatkit.model.Dialog
import com.stfalcon.chatkit.dialogs.DialogsListAdapter

class NewDialogViewHolder(itemView: View?) : DialogsListAdapter.DialogViewHolder<Dialog>(itemView) {
    val onlineIndicator: View? = itemView?.findViewById(R.id.onlineIndicator)

    override fun onBind(dialog: Dialog) {
        super.onBind(dialog)
        if (dialog.users?.size!! > 1) {
            onlineIndicator!!.visibility = View.GONE
        } else {
            val isOnline = dialog.users?.get(0)?.isOnline()!!
            onlineIndicator!!.visibility = View.VISIBLE
            if (isOnline) {
                onlineIndicator!!.setBackgroundResource(R.drawable.shape_bubble_online)
            } else {
                onlineIndicator!!.setBackgroundResource(R.drawable.shape_bubble_offline)
            }
        }
    }
}