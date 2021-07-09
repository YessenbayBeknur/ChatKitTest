package com.example.mychatkit.messages

import android.view.View
import android.widget.ImageView
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
}