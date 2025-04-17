package com.example.tiktokandroid.ui.post

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment
import com.example.tiktokandroid.databinding.FragmentCreatepostFormBinding

class CreatePostForm : DialogFragment() {

    private var _binding: FragmentCreatepostFormBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCreatepostFormBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val submitButton: Button = binding.createPostSubmitButton
        submitButton.setOnClickListener {
            val postText = binding.postTextInput.text.toString()
            val result = Bundle().apply {
                putString("postText", postText)
            }

            parentFragmentManager.setFragmentResult("createPostForm", result)
            dismiss()
        }

        val cancelButton: Button = binding.createPostTextCancelButton
        cancelButton.setOnClickListener {
            dismiss()
        }

        return root
    }

}