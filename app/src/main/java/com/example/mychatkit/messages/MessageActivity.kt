package com.example.mychatkit.messages

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.mychatkit.*
import com.example.mychatkit.model.Message
import com.example.mychatkit.model.MessagesFixtures
import com.squareup.picasso.Picasso
import com.stfalcon.chatkit.commons.ImageLoader
import com.stfalcon.chatkit.messages.MessageHolders
import com.stfalcon.chatkit.messages.MessageInput
import com.stfalcon.chatkit.messages.MessageInput.AttachmentsListener
import com.stfalcon.chatkit.messages.MessageInput.InputListener
import com.stfalcon.chatkit.messages.MessagesList
import com.stfalcon.chatkit.messages.MessagesListAdapter
import java.text.SimpleDateFormat
import java.util.*

class MessageActivity: AppCompatActivity(),
    InputListener, AttachmentsListener, MessagesListAdapter.SelectionListener,
    MessagesListAdapter.OnLoadMoreListener, MessageInput.TypingListener {
    private var messagesList: MessagesList? = null

    private val TOTAL_MESSAGES_COUNT = 100

    protected val senderId = "0"
    protected var imageLoader: ImageLoader? = null
    protected var messagesAdapter: MessagesListAdapter<Message>? = null

    private var menu: Menu? = null
    var selectionCount = 0
    private var lastLoadedDate: Date? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_holder_messages)
        messagesList = findViewById(R.id.messagesList)
        initAdapter()
        val input: MessageInput = findViewById(R.id.input)
        input.setInputListener(this)
        input.setAttachmentsListener(this)
        input.setTypingListener(this)
        onLoadMore(1,1)
    }

    override fun onSubmit(input: CharSequence): Boolean {
        messagesAdapter!!.addToStart(
            MessagesFixtures().getTextMessage(input.toString()), true
        )
        return true
    }

    override fun onAddAttachments() {
        messagesAdapter!!.addToStart(MessagesFixtures().imageMessage, true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        this.menu = menu
        menuInflater.inflate(R.menu.chat_actions_menu, menu)
        onSelectionChanged(0)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_delete -> messagesAdapter!!.deleteSelectedMessages()
            R.id.action_copy -> {
                messagesAdapter!!.copySelectedMessagesText(this, getMessageStringFormatter(), true)
            }
        }
        return true
    }

    override fun onBackPressed() {
        if (selectionCount == 0) {
            super.onBackPressed()
        } else {
            messagesAdapter!!.unselectAllItems()
            selectionCount = 0
        }
    }

    private fun initAdapter() {

        imageLoader = ImageLoader { imageView: ImageView?, url: String?, payload: Any? ->
            Picasso.get().load(url).into(imageView)
        }
        var holdersConfig = MessageHolders()
            .setIncomingTextConfig(
                NewIncomingTextMessageViewHolder::class.java,
                        R.layout.item_custom_incoming_text_message)
            .setOutcomingTextConfig(
                NewOutcomingTextMessageViewHolder::class.java,
                        R.layout.item_custom_outcoming_text_message)
            .setIncomingImageConfig(
                NewIncomingImageMessageViewHolder::class.java,
                        R.layout.item_custom_incoming_image_message)
            .setOutcomingImageConfig(
                NewOutcomingImageMessageViewHolder::class.java,
                        R.layout.item_custom_outcoming_image_message)

        messagesAdapter = MessagesListAdapter(senderId, holdersConfig, imageLoader)
        messagesAdapter!!.enableSelectionMode(this);
        messagesAdapter!!.setLoadMoreListener(this)
        messagesList!!.setAdapter(messagesAdapter)
    }

    override fun onLoadMore(page: Int, totalItemsCount: Int) {
        Log.i("TAG", "onLoadMore: $page $totalItemsCount")
        if (totalItemsCount < TOTAL_MESSAGES_COUNT) {
            loadMessages()
        }
    }

    override fun onSelectionChanged(count: Int) {
        selectionCount = count
        menu!!.findItem(R.id.action_delete).isVisible = count > 0
        menu!!.findItem(R.id.action_copy).isVisible = count > 0
    }

    fun loadMessages() {
        Handler().postDelayed({
            val messages: ArrayList<Message> = MessagesFixtures().getMessages(lastLoadedDate)
            lastLoadedDate = messages[messages.size - 1].createdAt
            messagesAdapter!!.addToEnd(messages, false)
        }, 1000)
    }

    private fun getMessageStringFormatter(): MessagesListAdapter.Formatter<Message> {
        return MessagesListAdapter.Formatter<Message> { message: Message ->
            val createdAt =
                SimpleDateFormat("MMM d, EEE 'at' h:mm a", Locale.getDefault())
                    .format(message.getCreatedAt())
            var text: String = message.getText()
            if (text == null) text = "[attachment]"
            java.lang.String.format(
                Locale.getDefault(), "%s: %s (%s)",
                message.getUser().getName(), text, createdAt
            )
        }
    }

    override fun onStartTyping() {
        Log.v("MSG", "Typing")
    }

    override fun onStopTyping() {
        Log.v("MSG", "stop Typing")
    }
}