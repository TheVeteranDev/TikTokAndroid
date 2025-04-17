package com.example.tiktokandroid.ui.post

import com.google.type.Date

data class Post(
    var id: String,
    var username: String,
    var text: String,
    var video: String,
    var likeCount: Int,
    var commentCount: Int,
    var isLiked: Boolean,
    var comments: MutableList<Comment>,
    var createdAt: Long
) {
    // constructor required for firestore
    constructor() : this("", "", "", "", 0, 0, false, mutableListOf(), System.currentTimeMillis())

    fun toggleIsLiked(): Boolean {
        isLiked = !isLiked

        if (isLiked) {
            likeCount++
        } else {
            likeCount--
        }

        return isLiked
    }

}