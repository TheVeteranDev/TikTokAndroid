package com.example.tiktokandroid.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tiktokandroid.R
import com.example.tiktokandroid.models.Comment
import kotlin.random.Random

class CommentAdapter(val comments: MutableList<Comment>) : RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {

    class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val usernameTextView: TextView = itemView.findViewById(R.id.comment_author)
        val commentTextView: TextView = itemView.findViewById(R.id.comment_text)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_comment, parent, false) //make xml
        return CommentViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int){

        val currentComment = comments[position]
        holder.usernameTextView.text = currentComment.username
        holder.commentTextView.text = currentComment.text

        SharedData.data = comments //share comments list with post adapter
        //update data in post adapter here!!!!!

        }

    override fun getItemCount(): Int {
        return comments.size
    }

}