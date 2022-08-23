package com.trd.freenewsapp.homescreen

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.trd.freenewsapp.R
import com.trd.freenewsapp.constants.Constants.LOG_TAG
import com.trd.freenewsapp.databinding.FragmentHomeBinding
import com.trd.freenewsapp.homescreen.adapters.HomeAdapter
import com.trd.freenewsapp.homescreen.adapters.NewsItem
import com.trd.freenewsapp.listeners.BookmarkButtonListener
import com.trd.freenewsapp.listeners.ShareButtonsListener
import com.trd.freenewsapp.listeners.SourceButtonsListener
import com.trd.freenewsapp.states.BookmarksState.BookmarkAdded
import com.trd.freenewsapp.states.NewsState.*
import com.trd.freenewsapp.utils.ImageLoader
import com.trd.freenewsapp.utils.ToastUtils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.properties.Delegates


@AndroidEntryPoint
class HomeFragment : Fragment(), BookmarkButtonListener, SourceButtonsListener,
    ShareButtonsListener {
    private var binding by Delegates.notNull<FragmentHomeBinding>()
    private val viewModel: HomeViewModel by viewModels()
    private var adapter: HomeAdapter? = null
    private val cachedNews = mutableListOf<NewsItem>()

    @Inject
    lateinit var imageLoader: ImageLoader

    @Inject
    lateinit var toastUtils: ToastUtils

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.i(LOG_TAG, "onCreateView: HOME FRAGMENT")
        binding = initBinding(inflater, container)
        initAdapter()
        initRecyclerView()
        loadNews()
        initSearch()
        scrolledToBottomListener()
        initObservers()
        return binding.root
    }

    private fun initSearch() {
        val searchView = binding.searchHome
        val searchIcon = searchView.findViewById<ImageView>(androidx.appcompat.R.id.search_button)
        val closeIcon = searchView.findViewById<ImageView>(androidx.appcompat.R.id.search_close_btn)
        val searchEditText =
            searchView.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)

        val elementsColor = ContextCompat.getColor(requireContext(), R.color.text2)
        searchEditText.setTextColor(elementsColor)
        searchEditText.setHintTextColor(elementsColor)
        searchIcon.setColorFilter(elementsColor)
        closeIcon.setColorFilter(elementsColor)

        setOnQueryTextListener(searchView)
    }

    private fun setOnQueryTextListener(searchView: SearchView) {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { viewModel.loadNewsByQuery(it) }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun initAdapter() {
        adapter = HomeAdapter(requireContext(), imageLoader, this, this, this)
    }

    private fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater, container, false)
    }

    private fun initRecyclerView() {
        binding.newsRecyclerView.apply {
            adapter = this@HomeFragment.adapter
            layoutManager = LinearLayoutManager(context)
            itemAnimator = null
        }
    }

    private fun loadNews() {
        viewModel.loadNews()
//        testData()
    }

    private fun testData() {
        adapter?.setData(
            listOf(
                NewsItem("first"),
                NewsItem("red"),
                NewsItem("hello2"),
                NewsItem("hello3")
            )
        )
    }

    private fun initObservers() {
        observeNewsLoading()
        observeBookmarks()
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
                    cachedNews.addAll(newsState.newsItems)
                    adapter?.setData(cachedNews)
                }
            }
        }
    }

    private fun observeBookmarks() {
        viewModel.bookmarksLiveData.observe(viewLifecycleOwner) { bookmarkState ->
            when (bookmarkState) {
                is BookmarkAdded -> {
                    toastUtils.showToast(getString(R.string.bookmark_added_message))
                }
                else -> {}
            }
        }
    }

    private fun showProgressBar(loaded: Boolean) {
        binding.newsLoadingProgress.isVisible = loaded
    }


    override fun addBookmark(newsItem: NewsItem) {
        viewModel.addBookmark(newsItem)
    }

    override fun removeBookmark(newsItem: NewsItem) {
    }

    override fun shareBtnClicked(link: String) {
        shareNewsLink(link)
    }

    override fun sourceBtnClicked(url: String) {
        val action = HomeFragmentDirections.openWebView(url)
        val navController = findNavController()
        navController.navigate(action)
    }

    private fun shareNewsLink(link: String) {
        val shareIntent = Intent().apply {
            this.action = Intent.ACTION_SEND
            this.putExtra(Intent.EXTRA_TEXT, link)
            this.type = "text/plain"
        }
        startActivity(Intent.createChooser(shareIntent, getString(R.string.share_news_message)))
    }

    private fun scrolledToBottomListener() {
        binding.newsRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (!recyclerView.canScrollVertically(1)) {
                    toastUtils.showToast(getString(R.string.loading_news_message))
                    viewModel.increasePageNumber()
                    viewModel.loadMoreNews()
                }
            }
        })
    }

}