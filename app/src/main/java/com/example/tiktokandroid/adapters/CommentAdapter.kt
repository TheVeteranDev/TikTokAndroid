package com.example.tiktokandroid.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.example.tiktokandroid.R
import com.example.tiktokandroid.models.Comment
import kotlin.random.Random
import com.example.tiktokandroid.adapters.PostAdapter
import com.example.tiktokandroid.models.Post
import com.example.tiktokandroid.ui.home.HomeFragment

class CommentAdapter(private val context: Context, val comments: MutableList<Comment>) : RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {

    private lateinit var postAdapter: PostAdapter

    class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val usernameTextView: TextView = itemView.findViewById(R.id.comment_author)
        val commentTextView: TextView = itemView.findViewById(R.id.comment_text)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_comment, parent, false) //make xml
        return CommentViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {

        val currentComment = comments[position]
        holder.usernameTextView.text = currentComment.username
        holder.commentTextView.text = currentComment.text

        postAdapter = PostAdapter(context, SharedData.postList)
        postAdapter.notifyDataSetChanged()
        //update data in post adapter
    }

    override fun getItemCount(): Int {
            return comments.size
        }


}
