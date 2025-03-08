package com.example.tiktokandroid.models

import android.net.Uri
import com.example.tiktokandroid.adapters.SharedData

data class Post(
    val id: Int,
    val username: String,
    var text: String,
    val videoRaw: Int,
    var likeCount: Int,
    var commentCount: Int,
    var isLiked: Boolean,
    var comments: MutableList<Comment>? = null
) {
    val videoUri: Uri
        get() = Uri.parse("android.resource://com.example.tiktokandroid/${videoRaw}")

    fun toggleIsLiked(): Boolean {
        isLiked = !isLiked

        if (isLiked) {
            likeCount++
        } else {
            likeCount--
        }

        return isLiked
    }

    fun updateCommentCount(): Int {
        if (comments != null) {
            commentCount++
        }
        return commentCount
    }

}
