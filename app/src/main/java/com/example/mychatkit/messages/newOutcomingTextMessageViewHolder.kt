package com.example.mychatkit.messages

import android.view.View
import com.example.mychatkit.model.Message
import com.stfalcon.chatkit.messages.MessageHolders.OutcomingTextMessageViewHolder

class NewOutcomingTextMessageViewHolder(itemView: View?): OutcomingTextMessageViewHolder<Message>(itemView) {

    override fun onBind(message: Message) {
        super.onBind(message)
    }
}