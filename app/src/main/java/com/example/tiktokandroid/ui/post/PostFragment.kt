package com.example.tiktokandroid.ui.post

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.VideoView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.tiktokandroid.R
import com.example.tiktokandroid.databinding.FragmentPostBinding
import com.example.tiktokandroid.ui.home.HomeViewModel

class PostFragment : Fragment() {

    private var _binding: FragmentPostBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val postViewModel =
            ViewModelProvider(this).get(PostViewModel::class.java)

        _binding = FragmentPostBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val video: VideoView = binding.postVideoView

        val uri = Uri.parse("android.resource://${requireContext().packageName}/${R.raw.test_video}")
        video.setVideoURI(uri)

        val mediaController = MediaController(requireContext())
        mediaController.setAnchorView(video)
        video.setMediaController(mediaController)

        video.setOnPreparedListener { it.start() }

        return root
    }
}