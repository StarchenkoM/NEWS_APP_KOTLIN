package com.trd.freenewsapp.utils

import android.webkit.WebView
import android.webkit.WebViewClient

class WebViewLoaderImpl : WebViewLoader {
    override fun loadWebView(url: String, container: WebView) {
        container.apply {
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
            loadUrl(checkUrl(url))
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