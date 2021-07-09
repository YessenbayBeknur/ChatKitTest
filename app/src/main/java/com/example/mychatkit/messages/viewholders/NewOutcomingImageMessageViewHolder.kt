package com.example.mychatkit.messages.viewholders

import android.util.Log
import android.util.Pair
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.mychatkit.R
import com.example.mychatkit.model.Message
import com.example.mychatkit.model.QuotedMessage
import com.stfalcon.chatkit.messages.MessageHolders.OutcomingImageMessageViewHolder

class NewOutcomingImageMessageViewHolder(itemView: View?): OutcomingImageMessageViewHolder<Message>(itemView) {
    private var username: TextView? = itemView?.findViewById(R.id.txtUserItem)
    private var txt: TextView? = itemView?.findViewById(R.id.txtQuotedMsgItem)
    private var reply: ConstraintLayout? = itemView?.findViewById(R.id.reply_layoutItem)
    private var photo: ImageView? = itemView?.findViewById(R.id.imageQuoteItem)


    override fun onBind(message: Message) {
        super.onBind(message)
        val quotedMessage: QuotedMessage? = message.getQuoted()

        if (quotedMessage != null){
            username?.text = quotedMessage.userName
            txt?.text = quotedMessage.text
            reply?.visibility = View.VISIBLE
            if (quotedMessage.image != null){
                photo?.setImageDrawable(quotedMessage.image)
                photo?.visibility = View.VISIBLE
            } else photo?.visibility = View.GONE
        }
        else reply?.visibility = View.GONE
    }

    //Override this method to have ability to pass custom data in ImageLoader for loading image(not avatar).
    override fun getPayloadForImageLoader(message: Message?): Any? {
        //For example you can pass size of placeholder before loading
        return Pair(100, 100)
    }
}