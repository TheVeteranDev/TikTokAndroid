package com.example.tiktokandroid.models

import android.net.Uri

data class Post(
    val id: Int,
    val username: String,
    var text: String,
    val videoRaw: Int,
    var likeCount: Int,
    var commentCount: Int,
    var isLiked: Boolean
) {
    val videoUri: Uri
        get() = Uri.parse("android.resource://com.example.tiktokandroid/${videoRaw}")

    fun toggleIsLiked() {
        isLiked = !isLiked

        if (isLiked) {
            likeCount++
        } else {
            likeCount--
        }
    }

}
