package com.example.tiktokandroid.ui.post

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView
import com.example.tiktokandroid.R

class PostAdapter(private val posts: List<Post>) : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    class PostViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val username: TextView = view.findViewById(R.id.post_username_text_view)
        val text: TextView = view.findViewById(R.id.post_text_view)
        val video: VideoView = view.findViewById(R.id.post_video_view)
        val likeCount: TextView = view.findViewById(R.id.likes_count_text_view)
        val commentCount: TextView = view.findViewById(R.id.comments_count_text_view)
        val likeButton: ImageButton = view.findViewById(R.id.like_button)
        val commentsButton: ImageButton = view.findViewById(R.id.comments_button)

        fun bind(post: Post, postAdapter: PostAdapter) {
            username.text = post.username
            text.text = post.text
            likeCount.text = post.likeCount.toString()
            commentCount.text = post.commentCount.toString()
            video.setVideoURI(post.videoUri)
            video.pause()
            setLikeButtonState(post.isLiked)

            video.setOnPreparedListener { mediaPlayer ->
                mediaPlayer.isLooping = true
            }

            likeButton.setOnClickListener {
                setLikeButtonState(post.toggleIsLiked())
                likeCount.text = post.likeCount.toString()
            }

            commentsButton.setOnClickListener {
                commentCount.text = post.commentCount++.toString()
            }
        }

        fun setLikeButtonState(liked: Boolean) {
            if (liked) {
                likeButton.setImageResource(R.drawable.like_button_active)
            } else {
                likeButton.setImageResource(R.drawable.like_button)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_post, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val player = posts[position]

        // Keeps all the logic for setting values in the holder class
        holder.bind(player, this)
    }

    override fun getItemCount(): Int {
        return posts.size
    }
}