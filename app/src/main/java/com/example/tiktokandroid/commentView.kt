package com.example.tiktokandroid

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tiktokandroid.adapters.CommentAdapter
import com.example.tiktokandroid.adapters.SharedData
import com.example.tiktokandroid.models.Comment
import kotlin.random.Random

class CommentView : AppCompatActivity() {

    private lateinit var commentRecyclerView: RecyclerView

    private lateinit var commentAdapter: CommentAdapter

    private lateinit var mInputCommentEditText: EditText //box to input comment

    private lateinit var mSaveButton: Button //button to save comment

    private lateinit var mBackButton: Button //button to go back to post view

    private val commentList = mutableListOf<Comment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_comment) //Set detailed view UI

        val postId = intent.getIntExtra("postId",0)

        commentRecyclerView = findViewById(R.id.rv_comments) //must create view in xml

        commentAdapter = CommentAdapter(this, commentList)

        commentRecyclerView.adapter = commentAdapter
        commentRecyclerView.layoutManager = LinearLayoutManager(this)

        mInputCommentEditText = findViewById<EditText>(R.id.comment_field)
        mSaveButton = findViewById<Button>(R.id.save_comment_button)
        mBackButton = findViewById<Button>(R.id.back_button)

        SharedData.recentCount = 0

        var recentComments = getRecentComments(SharedData.postComments, postId, 5)
        if (recentComments != null) {
            recentComments.forEach {
                commentList.add(it)
                SharedData.recentCount++
            }
        }

        mSaveButton.setOnClickListener {
            val commentText = mInputCommentEditText.text.toString().trim()
            if (commentText.isNotEmpty()) {
                val newComment = Comment(Random.nextInt(0, 100000), commentText, "patcav1",
                    System.currentTimeMillis() / 1000, postId)
                commentList.add(newComment)
                commentAdapter.notifyItemInserted(commentList.size - 1)
                commentAdapter.notifyDataSetChanged()
                mInputCommentEditText.text.clear()
            }
        }

        mBackButton.setOnClickListener {

            /*if (recentComments != null) {
                recentComments.forEach {
                    SharedData.recentCount++
                    //commentList.remove(it)
                }
            }*/
            val intent = Intent(this, MainActivity::class.java)
            this.startActivity(intent)
        }

    }

    fun getRecentComments(comments: MutableList<Comment>?, postId: Int, limit: Int = 5): List<Comment>? {
        if (comments != null) {
            return comments.filter { it.postId == postId }
                .sortedByDescending { it.timestamp }
                .take(limit)
        }
        return (null)
    }
}


