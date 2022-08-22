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
import com.trd.freenewsapp.homescreen.adapters.HomeAdapter
import com.trd.freenewsapp.utils.ImageLoader
import com.trd.freenewsapp.utils.ToastUtils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.properties.Delegates

@AndroidEntryPoint
class BookmarkFragment : Fragment()/*, NoMatchesFoundListener*/ {
    private var binding by Delegates.notNull<FragmentBookmarksBinding>()
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
//        setHasOptionsMenu(true)
        binding = initialBinding(inflater, container)
//        initActionBar()
//        setBookmarkAdapter()
//        initRecyclerView()
//        initListeners()
//        initObservers()
        return binding.root
    }

    private fun setBookmarkAdapter(): HomeAdapter {
        return HomeAdapter(requireContext(), imageLoader)
    }

    private fun initialBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentBookmarksBinding {
        return FragmentBookmarksBinding.inflate(inflater, container, false)
    }

    private fun initRecyclerView() {
        binding.bookmarksRecyclerView.apply {
            adapter = homeAdapter
            layoutManager = LinearLayoutManager(context)
            itemAnimator = null
        }
    }

    private fun initListeners() {

    }

    private fun initObservers() {

    }


    private fun showProgressBar(newsLoaded: Boolean) {
        binding.bookmarksLoadingProgress.isVisible = !newsLoaded
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


    /*override fun noMatchesFound(noMatchesFound: Boolean) {
        binding.noUserFoundTextView.isVisible = noMatchesFound
    }*/


}