package com.trd.freenewsapp.utils

import android.graphics.Bitmap
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.view.isVisible

class WebViewLoaderImpl : WebViewLoader {
    override fun loadWebView(url: String, container: WebView, progressBar: View) {
        container.apply {
            webViewClient = provideWebViewClient(progressBar)
            settings.javaScriptEnabled = true
            loadUrl(checkUrl(url))
        }
    }

    private fun provideWebViewClient(progressBar: View) = object : WebViewClient() {
        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            progressBar.isVisible = true
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            progressBar.isVisible = false
        }
    }

    private fun checkUrl(url: String): String {
        return if (url.contains("https")) {
            url
        } else {
            url.replace("http", "https")
        }
    }

}