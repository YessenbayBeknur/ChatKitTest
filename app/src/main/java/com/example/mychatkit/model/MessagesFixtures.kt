package com.example.mychatkit.model

import java.util.*

class MessagesFixtures: FixturesData() {
        val imageMessage: Message
            get() {
                val message = Message(getRandomId(), user, null)
                message.setImage(Message.Image(getRandomImage()))
                return message
            }
        val voiceMessage: Message
            get() {
                val message = Message(getRandomId(), user, null)
                message.setVoice(Message.Voice("http://example.com", rnd.nextInt(200) + 30))
                return message
            }
        val textMessage: Message
            get() = getTextMessage(getRandomMessage())

        fun getTextMessage(text: String?): Message {
            return Message(getRandomId(), user, text)
        }

        fun getMessages(startDate: Date?): ArrayList<Message> {
            val messages: ArrayList<Message> = ArrayList<Message>()
            for (i in 0..9) {
                val countPerDay: Int = rnd.nextInt(5) + 1
                for (j in 0 until countPerDay) {
                    var message: Message
                    message = if (i % 2 == 0 && j % 3 == 0) {
                        imageMessage
                    } else {
                        textMessage
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