package com.example.tiktokandroid.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.VideoView
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.recyclerview.widget.RecyclerView
import com.example.tiktokandroid.R
import com.example.tiktokandroid.models.Comment
import com.example.tiktokandroid.models.Post
import com.example.tiktokandroid.CommentView
import com.example.tiktokandroid.MainActivity
import android.app.Activity
import com.example.tiktokandroid.ui.home.HomeFragment

class PostAdapter(private val context: Context, private val posts: List<Post>?) : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

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
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.fragment_post, parent, false)
        return PostViewHolder(view)

    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {

        val post = posts?.get(position)
        if (post != null) {
            setLikeButton(holder, post.isLiked)
        }

        if (post != null) {
            post.comments = SharedData.postComments
        } //get new comments list from comment adapter

        if (post != null) {
            if (post.comments?.size != null) {
                post.commentCount = post.commentCount + post.comments!!.size - SharedData.recentCount
            }
        }

        if (post != null) {
            holder.username.text = post.username
        }
        if (post != null) {
            holder.text.text = post.text
        }
        if (post != null) {
            holder.likeCount.text = post.likeCount.toString()
        }
        if (post != null) {
            holder.commentCount.text = post.commentCount.toString()
        }
        if (post != null) {
            holder.video.setVideoURI(post.videoUri)
        }
        holder.video.pause()

        holder.video.setOnPreparedListener { mediaPlayer ->
            mediaPlayer.isLooping = true
        }

        holder.likeButton.setOnClickListener {
            if (post != null) {
                setLikeButton(holder, post.toggleIsLiked())
            }
            if (post != null) {
                holder.likeCount.text = post.likeCount.toString()
            }
        }

        holder.commentsButton.setOnClickListener {
            //SharedData.postPos = position
            SharedData.postList = posts
            val intent = Intent(context, CommentView::class.java)
            if (post != null) {
                intent.putExtra("postId",post.id)
            }
            context.startActivity(intent)
            //Intent to go to edit comment activity
        }
    }

    override fun getItemCount(): Int {
        return posts?.size ?:0
    }

    fun setLikeButton(holder: PostViewHolder, isLiked: Boolean) {
        if (isLiked) {
            holder.likeButton.setImageResource(R.drawable.like_button_active)
        } else {
            holder.likeButton.setImageResource(R.drawable.like_button)
        }
    }

}



