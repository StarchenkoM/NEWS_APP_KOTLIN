package com.trd.freenewsapp.homescreen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
//import androidx.appcompat.R
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.trd.freenewsapp.R
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
//        homeAdapter?.setData(listOf(NewsItem("hello1"), NewsItem("hello2")))
        loadNews()
        initSearch()
//        initActionBar()
//        setHomeAdapter()
//        initRecyclerView()
        initObservers()
        return binding.root
    }

    private fun initSearch() {
        val searchView = binding.searchHome
        val searchIcon = searchView.findViewById<ImageView>(androidx.appcompat.R.id.search_button)
        val closeIcon = searchView.findViewById<ImageView>(androidx.appcompat.R.id.search_close_btn)

        val searchEditText =
            searchView.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
        val searchViewTextColor = ContextCompat.getColor(requireContext(), R.color.text_color)
//        val hintTextColor = ContextCompat.getColor(requireContext(), R.color.secondary)
        searchEditText.setTextColor(searchViewTextColor)

        val color = requireContext().getColor(R.color.text_color)
        searchIcon.setColorFilter(color)
        closeIcon.setColorFilter(color)
        setOnQueryTextListener(searchView)
    }

    private fun setOnQueryTextListener(searchView: SearchView) {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { viewModel.loadNewsByQuery(it) }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
//                toastUtils.showToast(newText?:"empty ")
                return false
            }
        })
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
        viewModel.loadNews()
    }

    private fun initObservers() {
        observeNewsLoading()
    }

    private fun observeNewsLoading() {
        viewModel.newsLiveData.observe(viewLifecycleOwner) { newsState ->
            when (newsState) {
                is NewsLoadingError -> {
                    showProgressBar(false)
                    toastUtils.showNetworkErrorToast()
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