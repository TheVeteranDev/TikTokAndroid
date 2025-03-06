package com.example.tiktokandroid.models

import android.net.Uri

data class Comment (
    var id: Int,
    var text: String,
    var isSaved: Boolean,
    var username: String
)
{}


