package com.example.mychatkit.messages

import android.view.View
import com.example.mychatkit.R
import com.example.mychatkit.model.Message
import com.stfalcon.chatkit.messages.MessageHolders.IncomingImageMessageViewHolder

class NewIncomingImageMessageViewHolder(itemView: View): IncomingImageMessageViewHolder<Message>(itemView){

    //private var onlineIndicator: View? = itemView.findViewById(R.id.onlineIndicator)

    override fun onBind(message: Message) {
        super.onBind(message)
        val isOnline: Boolean = message.getUser()?.isOnline()!!
        /*if (isOnline) {
            onlineIndicator!!.setBackgroundResource(R.drawable.shape_bubble_online)
        } else {
            onlineIndicator!!.setBackgroundResource(R.drawable.shape_bubble_offline)
        }*/
    }
}