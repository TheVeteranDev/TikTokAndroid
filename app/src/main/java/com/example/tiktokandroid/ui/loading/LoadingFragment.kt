package com.example.tiktokandroid.ui.loading

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.tiktokandroid.databinding.FragmentLoadingBinding

class LoadingFragment : Fragment() {
    private var _binding: FragmentLoadingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val loadingViewModel =
            ViewModelProvider(this).get(LoadingViewModel::class.java)

        _binding = FragmentLoadingBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val loadingSpinner: ProgressBar = binding.loadingSpinner

        return root
    }
}