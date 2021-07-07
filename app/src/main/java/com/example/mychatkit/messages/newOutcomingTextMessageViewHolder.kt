package com.example.mychatkit.messages

import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.mychatkit.R
import com.example.mychatkit.model.Message
import com.example.mychatkit.model.QuotedMessage
import com.stfalcon.chatkit.messages.MessageHolders.OutcomingTextMessageViewHolder

class NewOutcomingTextMessageViewHolder(itemView: View?): OutcomingTextMessageViewHolder<Message>(itemView) {
    private var username: TextView? = itemView?.findViewById(R.id.txtUserItem)
    private var txt: TextView? = itemView?.findViewById(R.id.txtQuotedMsgItem)
    private var reply: ConstraintLayout? = itemView?.findViewById(R.id.reply_layoutItem)

    override fun onBind(message: Message) {
        super.onBind(message)
        val quotedMessage: QuotedMessage? = message.getQuoted()

        if (quotedMessage != null){
            username?.text = quotedMessage.userName
            txt?.text = quotedMessage.text
            reply?.visibility = View.VISIBLE
        }
        else reply?.visibility = View.GONE
    }
}