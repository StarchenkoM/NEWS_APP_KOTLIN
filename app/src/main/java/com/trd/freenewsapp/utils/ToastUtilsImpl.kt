package com.trd.freenewsapp.utils

import android.content.Context
import android.widget.Toast
import com.trd.freenewsapp.R

class ToastUtilsImpl(val context: Context) : ToastUtils {
    override fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun showToast(messageResId: Int) {
        Toast.makeText(context, messageResId, Toast.LENGTH_LONG).show()
    }

    override fun showShortToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun showShortToast(messageResId: Int) {
        Toast.makeText(context, messageResId, Toast.LENGTH_SHORT).show()
    }

    override fun showNetworkErrorToast() {
        Toast.makeText(context, context.getString(R.string.no_internet_connection), Toast.LENGTH_LONG).show()
    }

    override fun showUnknownErrorToast() {
        Toast.makeText(context, context.getString(R.string.unknown_error), Toast.LENGTH_LONG).show()
    }

    override fun showTooManyRequestToast() {
        Toast.makeText(context, context.getString(R.string.too_many_requests), Toast.LENGTH_LONG).show()
    }
}