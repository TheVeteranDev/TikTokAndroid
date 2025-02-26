package com.example.tiktokandroid.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView
import com.example.tiktokandroid.R
import com.example.tiktokandroid.models.Post

class PostAdapter(private val posts: List<Post>) : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    class PostViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val username: TextView = view.findViewById(R.id.post_username_text_view)
        val text: TextView = view.findViewById(R.id.post_text_view)
        val video: VideoView = view.findViewById(R.id.post_video_view)
        val likeCount: TextView = view.findViewById(R.id.likes_count_text_view)
        val commentCount: TextView = view.findViewById(R.id.comments_count_text_view)
        val likeButton: ImageButton = view.findViewById(R.id.like_button)
        val commentsButton: ImageButton = view.findViewById(R.id.comments_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_post, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = posts[position]

        setLikeButton(holder, post.isLiked)

        holder.username.text = post.username
        holder.text.text = post.text
        holder.likeCount.text = post.likeCount.toString()
        holder.commentCount.text = post.commentCount.toString()
        holder.video.setVideoURI(post.videoUri)
        holder.video.pause()

        holder.video.setOnPreparedListener { mediaPlayer ->
            mediaPlayer.isLooping = true
        }

        holder.likeButton.setOnClickListener {
            setLikeButton(holder, post.toggleIsLiked())
            holder.likeCount.text = post.likeCount.toString()
        }

        holder.commentsButton.setOnClickListener {
            holder.commentCount.text = post.commentCount++.toString()
        }
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    fun setLikeButton(holder: PostViewHolder, isLiked: Boolean) {
        if (isLiked) {
            holder.likeButton.setImageResource(R.drawable.like_button_active)
        } else {
            holder.likeButton.setImageResource(R.drawable.like_button)
        }
    }
}



