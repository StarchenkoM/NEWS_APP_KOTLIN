package com.trd.freenewsapp.homescreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.trd.freenewsapp.databinding.FragmentBookmarksBinding
import com.trd.freenewsapp.homescreen.adapters.BookmarksAdapter
import com.trd.freenewsapp.homescreen.adapters.HomeAdapter
import com.trd.freenewsapp.homescreen.adapters.NewsItem
import com.trd.freenewsapp.listeners.BookmarkButtonListener
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
    ShareButtonsListener/*, NoMatchesFoundListener*/ {
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
//        setHasOptionsMenu(true)
        binding = initBinding(inflater, container)
        initAdapter()
        initRecyclerView()
        loadBookmarks()
//        initActionBar()
//        setBookmarkAdapter()
//        initListeners()
        initObservers()
        return binding.root
    }

    private fun initBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentBookmarksBinding.inflate(inflater, container, false)

    private fun initAdapter() {
        adapter = BookmarksAdapter(requireContext(), imageLoader, this, this, this)
//        binding.bookmarksRecyclerView.layoutManager = LinearLayoutManager(context)
//        binding.bookmarksRecyclerView.adapter = adapter
    }

    private fun loadBookmarks() {
        viewModel.loadBookmarks()
//        adapter?.setData(listOf(NewsItem(), NewsItem("hello2"), NewsItem("hello3")))
    }


    private fun initRecyclerView() {
        binding.bookmarksRecyclerView.apply {
            adapter = this@BookmarkFragment.adapter
            layoutManager = LinearLayoutManager(context)
            itemAnimator = null
        }
    }

    private fun initListeners() {

    }

    private fun initObservers() {
        viewModel.bookmarksLiveData.observe(viewLifecycleOwner) { bookmarkState ->
            when (bookmarkState) {
                is BookmarksLoadingError -> {
                    showProgressBar(false)
                }
                is BookmarksLoading -> {
                    showProgressBar(true)
                }
                is BookmarksLoadingSuccess -> {
                    showProgressBar(false)
                    adapter?.setData(bookmarkState.bookmarks)
                }
                is BookmarkAdded -> {
                    showProgressBar(false)
                    toastUtils.showToast("bookmark added BOOKMARK FRAGMENT")
                }
                is BookmarkRemoved -> {
                    showProgressBar(false)
                    toastUtils.showToast("bookmark removed BOOKMARK FRAGMENT")
                }
            }
        }
    }


    private fun showProgressBar(loaded: Boolean) {
        binding.bookmarksLoadingProgress.isVisible = loaded
    }


    private fun initActionBar() {
/*        val toolbar = binding.bookmarksToolbar
        toolbar.inflateMenu(R.menu.menu)
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        toolbar.setupWithNavController(navController, appBarConfiguration)*/
    }


    override fun onResume() {
        super.onResume()
//        viewModel.loadNews()
    }

    override fun addBookmark(newsItem: NewsItem) {
        viewModel.addBookmark(newsItem)
    }

    override fun removeBookmark(newsItem: NewsItem) {
        viewModel.removeBookmark(newsItem)
    }

    override fun shareBtnClicked() {

    }

    override fun sourceBtnClicked() {

    }


    /*override fun noMatchesFound(noMatchesFound: Boolean) {
        binding.noUserFoundTextView.isVisible = noMatchesFound
    }*/


}