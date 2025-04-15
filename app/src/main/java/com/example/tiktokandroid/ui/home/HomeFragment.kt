package com.example.tiktokandroid.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.VideoView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.tiktokandroid.R
import com.example.tiktokandroid.databinding.FragmentHomeBinding
import com.example.tiktokandroid.ui.post.Post
import com.example.tiktokandroid.ui.post.PostAdapter

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val posts: List<Post> = listOf(
            Post(1, "Philadelphia Eagles", "Saquon is unbelievable.  #Saquon", R.raw.cropped_saquon, 980, 26, false),
            Post(2, "Dragonball Z", "FAFO #DBZ", R.raw.cropped_we_are_the_ginyu, 530, 10, false),
            Post(3, "Angry Video Game Nerd", "Laughing Joking Numbnuts  #AVGN", R.raw.cropped_ljn, 330, 22, false)
        )

        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = PostAdapter(posts)

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}