package com.example.mychatkit.messages

import android.view.View
import com.example.mychatkit.R

import com.example.mychatkit.model.Message
import com.stfalcon.chatkit.messages.*

class NewIncomingTextMessageViewHolder(itemView: View?): MessageHolders.IncomingTextMessageViewHolder<Message>(itemView) {
    private var onlineIndicator: View? = itemView?.findViewById(R.id.onlineIndicator)


    override fun onBind(message: Message) {
        super.onBind(message)
        val isOnline = message.user.isOnline
        if (isOnline) {
            onlineIndicator!!.setBackgroundResource(R.drawable.shape_bubble_online)
        } else {
            onlineIndicator!!.setBackgroundResource(R.drawable.shape_bubble_offline)
        }

        //We can set click listener on view from payload
        /*val payload = this.payload as Payload
        userAvatar.setOnClickListener { view: View? ->
            payload.avatarClickListener.onAvatarClick()
        }*/
    }

    /*class Payload {
        lateinit var avatarClickListener: OnAvatarClickListener
    }

    interface OnAvatarClickListener {
        fun onAvatarClick()
    }*/
}