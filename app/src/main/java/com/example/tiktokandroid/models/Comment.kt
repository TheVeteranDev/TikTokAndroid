package com.example.tiktokandroid.models

data class Comment(
    var id: Int,
    var text: String,
    //var isSaved: Boolean,
    var username: String,
    val timestamp: Long,
    val postId: Int
)
{}


