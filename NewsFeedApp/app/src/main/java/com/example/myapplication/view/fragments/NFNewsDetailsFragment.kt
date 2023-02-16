package com.example.myapplication.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentNewsDetailsBinding
import com.example.myapplication.view.activities.MainActivity

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class NFNewsDetailsFragment : Fragment() {

    private var _binding: FragmentNewsDetailsBinding? = null
    private var url = "www.google.com"
    private var title = "Headlines details"

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentNewsDetailsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        url = arguments?.getString("url") ?: "www.google.com"
        title = arguments?.getString("title") ?: "Headlines details1"
        (requireActivity() as MainActivity).title = "My title"
        _binding?.webview?.loadUrl(url)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}