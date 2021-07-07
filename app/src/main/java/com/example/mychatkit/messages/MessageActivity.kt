package com.example.mychatkit.messages

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.mychatkit.*
import com.example.mychatkit.model.*
import com.squareup.picasso.Picasso
import com.stfalcon.chatkit.commons.ImageLoader
import com.stfalcon.chatkit.messages.MessageHolders
import com.stfalcon.chatkit.messages.MessageInput
import com.stfalcon.chatkit.messages.MessageInput.AttachmentsListener
import com.stfalcon.chatkit.messages.MessageInput.InputListener
import com.stfalcon.chatkit.messages.MessagesList
import com.stfalcon.chatkit.messages.MessagesListAdapter
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MessageActivity: AppCompatActivity(),
    InputListener, AttachmentsListener, MessagesListAdapter.SelectionListener,
    MessagesListAdapter.OnLoadMoreListener, MessageInput.TypingListener {
    private var messagesList: MessagesList? = null

    private val TOTAL_MESSAGES_COUNT = 100

    protected val senderId = "0"
    protected var imageLoader: ImageLoader? = null
    protected var messagesAdapter: MessagesListAdapter<Message>? = null

    private val you = User("0","Beka", "", false)

    var messages = ArrayList<Message>()

    lateinit var dialog: Dialog
    private var menu: Menu? = null
    var selectionCount = 0
    private var lastLoadedDate: Date? = null

    var quotedMessage: QuotedMessage? = null
    var isQuoted: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_holder_messages)
        messagesList = findViewById(R.id.messagesList)

        dialog = intent.getParcelableExtra<Dialog>("dialog")!!
        initAdapter()
        val input: MessageInput = findViewById(R.id.input)
        input.setInputListener(this)
        input.setAttachmentsListener(this)
        input.setTypingListener(this)
        onLoadMore(1,1)

        val messageSwipeController = MessageSwipeController(this, object : SwipeControllerActions {
            override fun showReplyUI(position: View) {
                val txt: TextView? =  (position as RelativeLayout).findViewById<TextView>(R.id.messageText)
                val quoteImage: ImageView? = (position as RelativeLayout).findViewById(R.id.image)
                if (txt != null) {
                    showQuotedMessage((position as RelativeLayout).findViewById<TextView>(R.id.messageText).text.toString() , input,
                        position.findViewById<TextView>(R.id.userName).text.toString())
                } else if (quoteImage != null){
                    showQuotedMessage("photo", input,
                        position.findViewById<TextView>(R.id.userName).text.toString() ,(position as RelativeLayout).findViewById<ImageView>(R.id.image).drawable)
                }
            }
        })

        val itemTouchHelper = ItemTouchHelper(messageSwipeController)
        itemTouchHelper.attachToRecyclerView(messagesList)

        findViewById<ImageButton>(R.id.cancelButton).setOnClickListener {
            hideReplyLayout()
        }

    }

    private fun hideReplyLayout() {
        findViewById<ConstraintLayout>(R.id.reply_layout).visibility = View.GONE
        isQuoted = false
    }

    private fun showQuotedMessage(message: String, messageInput: MessageInput, userName: String, drawable: Drawable? = null) {
        messageInput.requestFocus()
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(messageInput, InputMethodManager.SHOW_IMPLICIT)
        val quoteImage: ImageView = findViewById(R.id.imageQuote)
        if (drawable != null){
            quoteImage.setImageDrawable(drawable)
            quoteImage.visibility = View.VISIBLE
        }
        else {
            quoteImage.visibility = View.GONE
        }
        val txtQuotedMsg: TextView = findViewById(R.id.txtQuotedMsg)
        val reply_layout: ConstraintLayout = findViewById(R.id.reply_layout)
        val txtUser: TextView = findViewById(R.id.txtUser)
        txtUser.text = userName
        txtQuotedMsg.text = message
        reply_layout.visibility = View.VISIBLE

        isQuoted = true
        quotedMessage = QuotedMessage("0", userName, message, drawable)

        Log.i("AAAA", "$quotedMessage")
    }

    override fun onSubmit(input: CharSequence): Boolean {
        if (isQuoted){
            messagesAdapter!!.addToStart(
                MessagesFixtures().getQuotedMessage(input.toString(), quotedMessage!!, you), true)
                hideReplyLayout()
        } else {
            messagesAdapter!!.addToStart(
                MessagesFixtures().getTextMessage(input.toString(),you), true)
        }
        return true
    }

    override fun onAddAttachments() {
        if (isQuoted){
            messagesAdapter!!.addToStart(
                MessagesFixtures().getQuotedMessage(null, quotedMessage!!, you), true)
                hideReplyLayout()
        } else {
            messagesAdapter!!.addToStart(
                MessagesFixtures().getQuotedMessage(null ,null, you), true)
        }
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
        val holdersConfig = MessageHolders()
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
            messages = MessagesFixtures().getMessages(lastLoadedDate, dialog)
            lastLoadedDate = messages[messages.size - 1].createdAt
            messagesAdapter!!.addToEnd(messages, false)
        }, 1000)
    }

    private fun getMessageStringFormatter(): MessagesListAdapter.Formatter<Message> {
        return MessagesListAdapter.Formatter<Message> { message: Message ->
            val createdAt =
                SimpleDateFormat("MMM d, EEE 'at' h:mm a", Locale.getDefault())
                    .format(message.getCreatedAt()!!)
            var text: String = message.getText()!!
            if (text == null) text = "[attachment]"
            java.lang.String.format(
                Locale.getDefault(), "%s: %s (%s)",
                message.getUser()?.getName(), text, createdAt
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