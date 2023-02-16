package com.example.myapplication.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.NFApplication
import com.example.myapplication.R
import com.example.myapplication.viewmodel.NFTopHeadlinesViewModel
import com.example.myapplication.databinding.FragmentTopHeadlinesBinding
import com.example.myapplication.model.http.services.topheadlines.NFArticlesItem
import com.example.myapplication.utils.NFLogger
import com.example.myapplication.view.NFTopHeadlinesListAdapter
import com.example.myapplication.viewmodel.NFViewModelFactory

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class NFTopHeadlinesFragment : Fragment() {

    private var _binding: FragmentTopHeadlinesBinding? = null
    private var topHeadlinesAdapter: NFTopHeadlinesListAdapter? = null
    private var headlinesVM: NFTopHeadlinesViewModel? = null
    private var currentCountry = "us"

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentTopHeadlinesBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val app = activity?.application as NFApplication
        val factory = NFViewModelFactory(app, app.topHeadlinesRepo)
        headlinesVM = ViewModelProvider(this, factory).get(NFTopHeadlinesViewModel::class.java)
        registerObserver()
        binding.topHeadlinesRv.visibility = View.INVISIBLE
        activity?.findViewById<ProgressBar>(R.id.progress_circular1)?.visibility = View.VISIBLE
        headlinesVM?.getHeadLines(currentCountry)
        binding.refreshBtn.setOnClickListener {
            binding.topHeadlinesRv.visibility = View.INVISIBLE
            activity?.findViewById<ProgressBar>(R.id.progress_circular1)?.visibility = View.VISIBLE
            headlinesVM?.getHeadLines(currentCountry)

        }
    }

    private fun registerObserver() {
        headlinesVM?.headLinesLiveData?.observe(viewLifecycleOwner) {
            activity?.findViewById<ProgressBar>(R.id.progress_circular1)?.visibility =
                View.INVISIBLE
            binding.topHeadlinesRv.visibility = View.VISIBLE
            NFLogger.log(javaClass.name, it.toString())
            topHeadlinesAdapter = NFTopHeadlinesListAdapter(it,
                object : NFTopHeadlinesListAdapter.NFOnNewsItemClickListener {
                    override fun onClick(article: NFArticlesItem?) {
                        val bundle = bundleOf("url" to article?.url, "title" to article?.title)

                        findNavController().navigate(
                            R.id.action_TopHeadlinesFragment_to_DetailsFragment,
                            bundle
                        )
                    }

                })
            binding.topHeadlinesRv.apply {
                layoutManager = LinearLayoutManager(activity)
                adapter = topHeadlinesAdapter
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}