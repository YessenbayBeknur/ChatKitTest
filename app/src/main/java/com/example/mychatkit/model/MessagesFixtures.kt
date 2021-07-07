package com.example.mychatkit.model

import java.util.*
import kotlin.collections.ArrayList

class MessagesFixtures: FixturesData() {
        val imageMessage: Message
            get() {
                val message = Message(getRandomId(), user, null)
                message.setImage(getRandomImage()?.let { Message.Image(it) })
                return message
            }
        val voiceMessage: Message
            get() {
                val message = Message(getRandomId(), user, null)
                message.setVoice(Message.Voice("http://example.com", rnd.nextInt(200) + 30))
                return message
            }
        val textMessage: Message
            get() = getTextMessage(getRandomMessage(), user)

        fun getTextMessage(text: String?, user: User): Message {
            return Message(getRandomId(), user, text)
        }

        fun getQuotedMessage(text: String?, quotedMessage: QuotedMessage, user: User): Message {
            return Message(getRandomId(), user, text, quotedMessage=quotedMessage)
        }

        fun getMessages(startDate: Date?, dialog: Dialog): ArrayList<Message> {
            val messages: ArrayList<Message> = ArrayList<Message>()
            for (i in 0..9) {
                val countPerDay: Int = rnd.nextInt(5) + 1
                for (j in 0 until countPerDay) {
                    val even: Boolean = rnd.nextBoolean()
                    var message: Message
                    message = if (i % 2 == 0 && j % 3 == 0) {
                        imageMessage
                    } else {
                        if (even) {
                            Message(getRandomId(), dialog.users?.get((0..dialog.users?.size!!-1).random()), getRandomMessage(),
                                quotedMessage = QuotedMessage("a",dialog.users?.get((0..dialog.users?.size!!-1).random())?.getName()!!,getRandomMessage()))
                        } else
                            Message(getRandomId(), dialog.users?.get((0..dialog.users?.size!!-1).random()), getRandomMessage())
                    }
                    val calendar = Calendar.getInstance()
                    if (startDate != null) calendar.time = startDate
                    calendar.add(Calendar.DAY_OF_MONTH, -(i * i + 1))
                    message.setCreatedAt(calendar.time)
                    messages.add(message)
                }
            }
            return messages
        }

        private val user: User
            private get() {
                val even: Boolean = rnd.nextBoolean()
                return User(
                    if (even) "0" else "1",
                    if (even) names.get(0) else names.get(1),
                    if (even) avatars.get(0) else avatars.get(1),
                    true
                )
            }
}