package com.trd.freenewsapp.utils

import android.webkit.WebView

interface WebViewLoader {
    fun loadWebView(url: String, container: WebView)
}