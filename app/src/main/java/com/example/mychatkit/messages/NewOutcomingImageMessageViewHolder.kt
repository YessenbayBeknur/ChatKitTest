package com.example.mychatkit.messages

import android.util.Pair
import android.view.View
import com.example.mychatkit.model.Message
import com.stfalcon.chatkit.messages.MessageHolders.OutcomingImageMessageViewHolder

class NewOutcomingImageMessageViewHolder(itemView: View?): OutcomingImageMessageViewHolder<Message>(itemView) {

    override fun onBind(message: Message) {
        super.onBind(message)
        time.setText(message.getStatus().toString() + " " + time.getText())
    }

    //Override this method to have ability to pass custom data in ImageLoader for loading image(not avatar).
    override fun getPayloadForImageLoader(message: Message?): Any? {
        //For example you can pass size of placeholder before loading
        return Pair(100, 100)
    }
}