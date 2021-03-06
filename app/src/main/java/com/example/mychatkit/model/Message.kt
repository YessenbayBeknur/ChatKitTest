package com.example.mychatkit.model

import com.stfalcon.chatkit.commons.models.IMessage
import com.stfalcon.chatkit.commons.models.MessageContentType
import java.io.Serializable
import java.util.*

class Message(id: String?, user: User?, text: String?, createdAt: Date? = Date(), quotedMessage: QuotedMessage? = null): IMessage,
    MessageContentType.Image,
    MessageContentType, Serializable {

    private var id: String? = null
    private var text: String? = null
    private var createdAt: Date? = null
    private var user: User? = null
    private var image: Image? = null
    private var voice: Voice? = null
    private var quotedMessage: QuotedMessage? = null

    init  {
        this.id = id
        this.text = text
        this.user = user
        this.createdAt = createdAt
        this.quotedMessage = quotedMessage
    }

    fun getQuoted(): QuotedMessage? {
        return quotedMessage
    }

    override fun getId(): String? {
        return id
    }

    override fun getText(): String? {
        return text
    }

    override fun getCreatedAt(): Date? {
        return createdAt
    }

    override fun getUser(): User? {
        return user
    }

    override fun getImageUrl(): String? {
        return if (image == null) null else image!!.url
    }

    fun getVoice(): Voice? {
        return voice
    }

    fun getStatus(): String? {
        return "Sent"
    }

    fun setText(text: String?) {
        this.text = text
    }

    fun setCreatedAt(createdAt: Date?) {
        this.createdAt = createdAt
    }

    fun setImage(image: Image?) {
        this.image = image
    }

    fun setVoice(voice: Voice?) {
        this.voice = voice
    }

    class Image(val url: String)

    class Voice(val url: String, val duration: Int)
}