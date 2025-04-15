package com.example.tiktokandroid.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.VideoView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.tiktokandroid.R
import com.example.tiktokandroid.databinding.FragmentHomeBinding
import com.example.tiktokandroid.ui.post.Comment
import com.example.tiktokandroid.ui.post.Post
import com.example.tiktokandroid.ui.post.PostAdapter
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    // Get the firestore instance to be able to query the database
    private val db = FirebaseFirestore.getInstance()

    private var posts = mutableListOf<Post>()
    private var postAdapter = PostAdapter(posts)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = postAdapter

        fetchPosts()

        val snap = PagerSnapHelper()
        snap.attachToRecyclerView(recyclerView)

        recyclerView.addOnChildAttachStateChangeListener(object : RecyclerView.OnChildAttachStateChangeListener {
            override fun onChildViewAttachedToWindow(view: View) {
                // Find the position of the attached view
                val position = recyclerView.getChildAdapterPosition(view)
                val videoView = view.findViewById<VideoView>(R.id.post_video_view)

                // Start the video if it's the one in view
                if (position != RecyclerView.NO_POSITION) {
                    videoView.start()
                }
            }

            override fun onChildViewDetachedFromWindow(view: View) {
                val videoView = view.findViewById<VideoView>(R.id.post_video_view)
                // Pause the video when it scrolls out of view
                videoView?.pause()
            }
        })

        return root
    }

    private fun fetchPosts() {
        lifecycleScope.launch {
            posts.clear()
            val postDocs = db.collection("posts").get().await()
            for (doc in postDocs) {
                val post = doc.toObject(Post::class.java).apply { id = doc.id }

                val commentsSnap = db.collection("posts/${doc.id}/comments").get().await()
                for (commentDoc in commentsSnap) {
                    val comment =
                        commentDoc.toObject(Comment::class.java).apply { id = commentDoc.id }
                    post.comments.add(comment)
                }

                posts.add(post)
            }
            postAdapter.notifyDataSetChanged()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}