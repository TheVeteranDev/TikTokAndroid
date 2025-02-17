package com.example.tiktokandroid.ui.post

import android.net.Uri

data class Post(
    private val username: String,
    private val text: String,
    private val videoRaw: Int,
    private val likeCount: Int,
    private val commentCount: Int,
) {
    fun getUsername(): String {
        return username
    }

    fun getText(): String {
        return text
    }

    fun getVideoRawUri(): Uri {
        return Uri.parse("android.resource://com.example.tiktokandroid/${videoRaw}")
    }

    fun getLikeCount(): Int {
        return likeCount
    }

    fun getCommentCount(): Int {
        return commentCount
    }
}
