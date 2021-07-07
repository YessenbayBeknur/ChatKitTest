package com.example.mychatkit.model

import android.os.Parcel
import android.os.Parcelable
import com.stfalcon.chatkit.commons.models.IDialog
import java.util.*
import kotlin.collections.ArrayList

class Dialog(id: String?, name: String?, photo: String?,
                   users: ArrayList<User>?, lastMessage: Message?, unreadCount: Int?) : IDialog<Message>, Parcelable {

    private var id: String? = null
    private var dialogPhoto: String? = null
    private var dialogName: String? = null
    private var users: ArrayList<User>? = null
    private var lastMessage: Message? = null

    private var unreadCount: Int? = null

    constructor(parcel: Parcel) : this(
        null,null,null,null,null,null
    ) {
        id = parcel.readString()
        dialogPhoto = parcel.readString()
        dialogName = parcel.readString()
        unreadCount = parcel.readInt()
        users = parcel.readArrayList(User::class.java.classLoader) as ArrayList<User>

    }


    init {
        this.id = id
        dialogName = name
        dialogPhoto = photo
        this.users = users
        this.lastMessage = lastMessage
        this.unreadCount = unreadCount
    }

    override fun getId(): String? {
        return id
    }

    override fun getDialogPhoto(): String? {
        return dialogPhoto
    }

    override fun getDialogName(): String? {
        return dialogName
    }

    override fun getUsers(): ArrayList<User>? {
        return users
    }

    override fun getLastMessage(): Message? {
        return lastMessage
    }

    override fun setLastMessage(lastMessage: Message?) {
        this.lastMessage = lastMessage
    }

    override fun getUnreadCount(): Int {
        return unreadCount!!
    }

    fun setUnreadCount(unreadCount: Int) {
        this.unreadCount = unreadCount
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(dialogPhoto)
        parcel.writeString(dialogName)
        parcel.writeInt(unreadCount!!)
        parcel.writeList(users)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Dialog> {
        override fun createFromParcel(parcel: Parcel): Dialog {
            return Dialog(parcel)
        }

        override fun newArray(size: Int): Array<Dialog?> {
            return arrayOfNulls(size)
        }
    }
}