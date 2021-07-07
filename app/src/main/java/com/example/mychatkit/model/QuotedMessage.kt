package com.example.mychatkit.model

import android.graphics.drawable.Drawable

data class QuotedMessage(val id: String?,val userName: String?,val text: String?, val image: Drawable? = null) {
}