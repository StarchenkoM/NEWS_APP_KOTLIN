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
import com.trd.freenewsapp.R
import com.trd.freenewsapp.databinding.FragmentBookmarksBinding
import com.trd.freenewsapp.homescreen.adapters.BookmarksAdapter
import com.trd.freenewsapp.homescreen.adapters.NewsItem
import com.trd.freenewsapp.listeners.BookmarkButtonListener
import com.trd.freenewsapp.listeners.SearchNoMatchesListener
import com.trd.freenewsapp.listeners.ShareButtonsListener
import com.trd.freenewsapp.listeners.SourceButtonsListener
import com.trd.freenewsapp.states.BookmarksState.*
import com.trd.freenewsapp.utils.ImageLoader
import com.trd.freenewsapp.utils.ToastUtils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.properties.Delegates

@AndroidEntryPoint
class BookmarkFragment : Fragment(), BookmarkButtonListener, SourceButtonsListener,
    ShareButtonsListener, SearchNoMatchesListener {
    private var binding by Delegates.notNull<FragmentBookmarksBinding>()
    private val viewModel: BookmarksViewModel by viewModels()
    private var adapter: BookmarksAdapter? = null

    @Inject
    lateinit var imageLoader: ImageLoader

    @Inject
    lateinit var toastUtils: ToastUtils

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = initBinding(inflater, container)
        initAdapter()
        initRecyclerView()
        loadBookmarks()
        initObservers()
        initSearch()
        return binding.root
    }

    private fun initBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentBookmarksBinding.inflate(inflater, container, false)

    private fun initAdapter() {
        adapter = BookmarksAdapter(
            imageLoader,
            bookmarkButtonListener = this,
            shareButtonsListener = this,
            sourceButtonsListener = this,
            searchNoMatchesListener = this
        )
    }

    private fun loadBookmarks() {
        viewModel.loadBookmarks()
    }

    private fun initRecyclerView() {
        binding.bookmarksRecyclerView.apply {
            adapter = this@BookmarkFragment.adapter
            layoutManager = LinearLayoutManager(context)
            itemAnimator = null
        }
    }

    private fun initSearch() {
        val searchView = binding.searchBookmarks
        setSearchElementColors(searchView)
        setOnQueryTextListener(searchView)
    }

    private fun setSearchElementColors(searchView: SearchView) {
        val elementsColor = ContextCompat.getColor(requireContext(), R.color.text2)

        val searchIcon = searchView.findViewById<ImageView>(androidx.appcompat.R.id.search_button)
        val closeIcon = searchView.findViewById<ImageView>(androidx.appcompat.R.id.search_close_btn)
        val searchEditText =
            searchView.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)

        searchEditText.setTextColor(elementsColor)
        searchEditText.setHintTextColor(elementsColor)
        searchIcon.setColorFilter(elementsColor)
        closeIcon.setColorFilter(elementsColor)
    }

    private fun setOnQueryTextListener(searchView: SearchView) {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter?.filter?.filter(newText)
                return false
            }
        })
    }

    private fun initObservers() {
        viewModel.bookmarksLiveData.observe(viewLifecycleOwner) { bookmarkState ->
            when (bookmarkState) {
                is BookmarksLoadingError -> {
                    showProgressBar(false)
                    binding.emptyBookmarksTv.isVisible = true
                }
                is BookmarksLoading -> {
                    showProgressBar(true)
                }
                is BookmarksLoadingSuccess -> {
                    showProgressBar(false)
                    binding.emptyBookmarksTv.isVisible = bookmarkState.bookmarks.isNullOrEmpty()
                    adapter?.setData(bookmarkState.bookmarks)
                }
                is BookmarkAdded -> {
                    showProgressBar(false)
                    toastUtils.showToast("bookmark added")
                }
                is BookmarkRemoved -> {
                    showProgressBar(false)
                    toastUtils.showToast("bookmark removed")
                }
            }
        }
    }


    private fun showProgressBar(loaded: Boolean) {
        binding.bookmarksLoadingProgress.isVisible = loaded
    }

    override fun addBookmark(newsItem: NewsItem) {
        viewModel.addBookmark(newsItem)
    }

    override fun removeBookmark(newsItem: NewsItem) {
        viewModel.removeBookmark(newsItem)
    }

    override fun shareBtnClicked(link: String) {
        shareNewsLink(link)
    }

    override fun sourceBtnClicked(url: String) {
        val action = BookmarkFragmentDirections.openWebView(url)
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

    override fun searchNoMatches(isMatchesFound: Boolean) {
        binding.noMatchesTv.isVisible = isMatchesFound
    }


}