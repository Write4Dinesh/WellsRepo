package com.example.myapplication.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
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
    private var swipeRefreshLayout: SwipeRefreshLayout? = null

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
        headlinesVM = ViewModelProvider(this, NFViewModelFactory(app, app.topHeadlinesRepo))
            .get(NFTopHeadlinesViewModel::class.java)
        registerLiveDataObserver()
        headlinesVM?.getHeadLines(currentCountry)

        binding.refreshBtn.setOnClickListener {
            headlinesVM?.getHeadLines(currentCountry)

        }

    }

    override fun onResume() {
        configurePullToRefresh()
        super.onResume()
    }

    private fun configurePullToRefresh() {
        swipeRefreshLayout = activity?.findViewById(R.id.pullToRefresh)
        swipeRefreshLayout?.setOnRefreshListener {
            headlinesVM?.getHeadLines(currentCountry)
        }
    }

    private fun registerLiveDataObserver() {
        headlinesVM?.headLinesLiveData?.observe(viewLifecycleOwner) {
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
        headlinesVM?.isLoadingLiveData?.observe(viewLifecycleOwner) {
            hideOrShowProgressBar(it)
        }
        headlinesVM?.errorLiveData?.observe(viewLifecycleOwner) {
            Toast.makeText(activity, "Error $it", Toast.LENGTH_LONG).show()
        }
    }

    private fun hideOrShowProgressBar(show: Boolean) {
        if (!show) {
            swipeRefreshLayout?.isRefreshing = false
        }
        activity?.findViewById<ProgressBar>(R.id.progress_circular1)?.visibility =
            if (show) View.VISIBLE else View.INVISIBLE
        binding.topHeadlinesRv.visibility = if (show) View.INVISIBLE else View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}