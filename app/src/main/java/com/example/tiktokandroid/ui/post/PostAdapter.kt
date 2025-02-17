package com.example.tiktokandroid.ui.post

import android.content.Context
import android.media.AudioManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.VideoView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tiktokandroid.R

class PostAdapter(private val posts: List<Post>) : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    class PostViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val postUsername: TextView = view.findViewById(R.id.post_username_text_view)
        val postText: TextView = view.findViewById(R.id.post_text_view)
        val postVideo: VideoView = view.findViewById(R.id.post_video_view)
        val postLikeCount: TextView = view.findViewById(R.id.likes_count_text_view)
        val postCommentCount: TextView = view.findViewById(R.id.comments_count_text_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_post, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = posts[position]

        holder.postUsername.text = post.getUsername()
        holder.postText.text = post.getText()
        holder.postLikeCount.text = post.getLikeCount().toString()
        holder.postCommentCount.text = post.getCommentCount().toString()
        holder.postVideo.setVideoURI(post.getVideoRawUri())
        holder.postVideo.pause()

        holder.postVideo.setOnPreparedListener { mediaPlayer ->
            mediaPlayer.isLooping = true
        }
    }

    override fun getItemCount(): Int {
        return posts.size
    }
}

