package com.example.mychatkit.model

import java.util.*

class DialogsFixtures: FixturesData() {
    private fun DialogsFixtures() {
        throw AssertionError()
    }

    fun getDialogs(): ArrayList<Dialog>? {
        val chats: ArrayList<Dialog> = ArrayList<Dialog>()
        for (i in 0..19) {
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.DAY_OF_MONTH, -(i * i))
            calendar.add(Calendar.MINUTE, -(i * i))
            chats.add(getDialog(i, calendar.time))
        }
        return chats
    }

    private fun getDialog(i: Int, lastMessageCreatedAt: Date): Dialog {
        val users: ArrayList<User> = getUsers()
        return Dialog(
            getRandomId(),
            if (users.size > 1) groupChatTitles[users.size - 2] else users[0].getName(),
            if (users.size > 1) groupChatImages[users.size - 2] else getRandomAvatar(),
            users,
            getMessage(lastMessageCreatedAt),
            if (i < 3) 3 - i else 0
        )
    }

    private fun getUsers(): ArrayList<User> {
        val users: ArrayList<User> = ArrayList<User>()
        val usersCount = 1 + rnd.nextInt(4)
        for (i in 0 until usersCount) {
            users.add(getUser())
        }
        return users
    }

    private fun getUser(): User {
        return User(
            getRandomId(),
            getRandomName(),
            getRandomAvatar(),
            getRandomBoolean()
        )
    }

    private fun getMessage(date: Date): Message? {
        return Message(
            getRandomId(),
            getUser(),
            getRandomMessage(),
            date
        )
    }
}