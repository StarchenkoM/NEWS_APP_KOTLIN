package com.trd.freenewsapp.homescreen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.trd.freenewsapp.constants.Constants.LOG_TAG
import com.trd.freenewsapp.databinding.FragmentHomeBinding
import com.trd.freenewsapp.homescreen.NewsState.*
import com.trd.freenewsapp.homescreen.adapter.HomeAdapter
import com.trd.freenewsapp.homescreen.adapter.NewsItem
import com.trd.freenewsapp.utils.ImageLoader
import com.trd.freenewsapp.utils.ToastUtils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.properties.Delegates

@AndroidEntryPoint
class HomeFragment : Fragment()/*, NoMatchesFoundListener*/ {
    private var binding by Delegates.notNull<FragmentHomeBinding>()
    private val viewModel: HomeViewModel by viewModels()
    private var homeAdapter: HomeAdapter? = null

    @Inject
    lateinit var imageLoader: ImageLoader

    @Inject
    lateinit var toastUtils: ToastUtils

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.i(LOG_TAG, "onCreateView: HOME FRAGMENT")
//        setHasOptionsMenu(true)
        binding = initialBinding(inflater, container)
        initAdapter()
        homeAdapter?.setData(listOf(NewsItem("hello1"), NewsItem("hello2")))
        loadNews()
//        initActionBar()
//        setHomeAdapter()
//        initRecyclerView()
        initObservers()
        return binding.root
    }

    private fun initAdapter() {
        homeAdapter = HomeAdapter(requireContext(), imageLoader)
        binding.newsRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.newsRecyclerView.adapter = homeAdapter
    }

    private fun setHomeAdapter(): HomeAdapter {
        return HomeAdapter(requireContext(), imageLoader)
    }

    private fun initialBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater, container, false)
    }

    private fun initRecyclerView() {
        binding.newsRecyclerView.apply {
//            adapter = homeAdapter
            adapter = HomeAdapter(requireContext(), imageLoader)
            layoutManager = LinearLayoutManager(context)
            itemAnimator = null
        }
    }

    private fun loadNews() {
//        viewModel.loadNews()
    }

    private fun initObservers() {
        observeNewsLoading()
    }

    private fun observeNewsLoading() {
        viewModel.newsLiveData.observe(viewLifecycleOwner) { newsState ->
            when (newsState) {
                is NewsLoadingError -> {/*TODO show toast here*/
                }
                is NewsLoading -> {
                    showProgressBar(true)
                }
                is NewsLoadingSuccess -> {
                    showProgressBar(false)
                    Log.i(
                        LOG_TAG,
                        "observeNewsLoading: HOME FRAGMENT newsState.newsItems = ${newsState.newsItems}"
                    )
                    val l = newsState.newsItems
//                    homeAdapter?.setData(listOf(NewsItem("hello1"), NewsItem("hello2")))
                    homeAdapter?.setData(l)
                }
            }
        }
    }


    private fun showProgressBar(loaded: Boolean) {
        binding.newsLoadingProgress.isVisible = loaded
    }


    private fun initActionBar() {
/*        val toolbar = binding.newsToolbar
        toolbar.inflateMenu(R.menu.menu)
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        toolbar.setupWithNavController(navController, appBarConfiguration)*/
    }


    override fun onResume() {
        super.onResume()
//        viewModel.loadNews()
    }


    /*override fun noMatchesFound(noMatchesFound: Boolean) {
        binding.noUserFoundTextView.isVisible = noMatchesFound
    }*/


}