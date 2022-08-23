package com.trd.freenewsapp.homescreen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.trd.freenewsapp.constants.Constants.LOG_TAG
import com.trd.freenewsapp.databinding.FragmentWebviewBinding
import com.trd.freenewsapp.utils.ToastUtils
import com.trd.freenewsapp.utils.WebViewLoader
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.properties.Delegates

@AndroidEntryPoint
class WebViewFragment : Fragment() {
    private var binding by Delegates.notNull<FragmentWebviewBinding>()
    private val args: WebViewFragmentArgs by navArgs()

    @Inject
    lateinit var webViewLoader: WebViewLoader

    @Inject
    lateinit var toastUtils: ToastUtils

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.i(LOG_TAG, "onCreateView: WebViewFragment")
        binding = initBinding(inflater, container)
        loadContent()
        return binding.root
    }

    private fun initBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentWebviewBinding.inflate(inflater, container, false)


    private fun loadContent() {
        webViewLoader.loadWebView(args.url, binding.webViewContainer)
    }


    private fun showProgressBar(loaded: Boolean) {
        binding.bookmarksLoadingProgress.isVisible = loaded
    }


}