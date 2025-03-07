package com.example.tiktokandroid.adapters

import android.content.Context
import com.example.tiktokandroid.models.Comment
import com.example.tiktokandroid.models.Post

object SharedData {
        var postComments: MutableList<Comment>? =null
        var postList: List<Post>? =null
        var recentCount: Int = 0
    }
