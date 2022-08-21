package com.trd.freenewsapp.utils

import androidx.annotation.StringRes

interface ToastUtils {
    fun showToast(message: String)
    fun showToast(@StringRes messageResId: Int)
    fun showShortToast(message: String)
    fun showShortToast(@StringRes messageResId: Int)
    fun showNetworkErrorToast()
    fun showUnknownErrorToast()
    fun showTooManyRequestToast()
}