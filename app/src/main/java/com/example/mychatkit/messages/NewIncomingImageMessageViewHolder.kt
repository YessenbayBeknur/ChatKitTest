package com.example.mychatkit.messages

import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.mychatkit.R
import com.example.mychatkit.model.Message
import com.example.mychatkit.model.QuotedMessage
import com.stfalcon.chatkit.messages.MessageHolders.IncomingImageMessageViewHolder

class NewIncomingImageMessageViewHolder(itemView: View): IncomingImageMessageViewHolder<Message>(itemView){
    private var username: TextView? = itemView?.findViewById(R.id.userName)
    private var usernameQuote: TextView? = itemView?.findViewById(R.id.txtUserItem)
    private var txt: TextView? = itemView?.findViewById(R.id.txtQuotedMsgItem)
    private var reply: ConstraintLayout? = itemView?.findViewById(R.id.reply_layoutItem)

    override fun onBind(message: Message) {
        super.onBind(message)
        username?.text = message.user?.name

        val quotedMessage: QuotedMessage? = message.getQuoted()

        Log.i("AAAA", "$quotedMessage")

        if (quotedMessage != null){
            usernameQuote?.text = quotedMessage.userName
            txt?.text = quotedMessage.text
            reply?.visibility = View.VISIBLE
        }
        else reply?.visibility = View.GONE
    }
}