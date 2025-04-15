package com.example.tiktokandroid.ui.post

data class Comment(
    var id: String,
    var text: String,
    var likeCount: Int
) {
    constructor(): this("", "", 0)
}
