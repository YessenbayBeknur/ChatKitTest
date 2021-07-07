package com.example.mychatkit.model

import com.stfalcon.chatkit.commons.models.IUser
import java.io.Serializable

class User(id: String?, name: String?, avatar: String?, online: Boolean): IUser, Serializable {
    private var id: String? = null
    private var name: String? = null
    private var avatar: String? = null
    private var online = false

    init {
        this.id = id
        this.name = name
        this.avatar = avatar
        this.online = online
    }

    override fun getId(): String? {
        return id
    }

    override fun getName(): String? {
        return name
    }

    override fun getAvatar(): String? {
        return avatar
    }

    fun isOnline(): Boolean {
        return online
    }
}