package com.example.tiktokandroid.ui.post

data class Post(
    var id: String,
    val username: String,
    var text: String,
    val video: String,
    var likeCount: Int,
    var commentCount: Int,
    var isLiked: Boolean
) {
    // constructor required for firestore
    constructor() : this("", "", "", "", 0, 0, false)

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