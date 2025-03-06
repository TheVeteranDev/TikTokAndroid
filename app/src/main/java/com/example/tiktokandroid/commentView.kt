package com.example.tiktokandroid

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tiktokandroid.adapters.CommentAdapter
import com.example.tiktokandroid.adapters.PostAdapter
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

        commentRecyclerView = findViewById(R.id.rv_comments) //must create view in xml

        commentAdapter = CommentAdapter(commentList)

        commentRecyclerView.adapter = commentAdapter
        commentRecyclerView.layoutManager = LinearLayoutManager(this)

        mInputCommentEditText = findViewById<EditText>(R.id.comment_field)
        mSaveButton = findViewById<Button>(R.id.save_comment_button)
        mBackButton = findViewById<Button>(R.id.back_button)

        mSaveButton.setOnClickListener {
            val commentText = mInputCommentEditText.text.toString().trim()
            if (commentText.isNotEmpty()) {
                val newComment = Comment(Random.nextInt(0, 100000), commentText, true, "patcav1")
                commentList.add(newComment)
                commentAdapter.notifyItemInserted(commentList.size - 1)
                mInputCommentEditText.text.clear()
            }
        }

        mBackButton.setOnClickListener {
            //finish() //this takes you back to black screen...why???
            val intent = Intent(this, MainActivity::class.java)
            //intent.putExtra("postPosition", position)
            this.startActivity(intent)
        }

        //commentList.add(Comment(Random.nextInt(0, 100000), "Awesome post!", true, "kato5"))
        //commentList.add(Comment(Random.nextInt(0, 100000), "Are you sure this is real?", true, "george67"))
        commentAdapter.notifyDataSetChanged()
    }
}


