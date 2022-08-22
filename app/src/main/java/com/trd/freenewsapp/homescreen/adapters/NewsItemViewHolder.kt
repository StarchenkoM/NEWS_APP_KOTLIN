package com.trd.freenewsapp.homescreen.adapters

import android.util.Log
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import com.trd.freenewsapp.R
import com.trd.freenewsapp.constants.Constants
import com.trd.freenewsapp.constants.Constants.LOG_TAG
import com.trd.freenewsapp.databinding.NewsItemBinding
import com.trd.freenewsapp.listeners.BookmarkButtonListener
import com.trd.freenewsapp.listeners.ShareButtonsListener
import com.trd.freenewsapp.listeners.SourceButtonsListener
import com.trd.freenewsapp.utils.ImageLoader
import com.trd.freenewsapp.utils.ToastUtils
import javax.inject.Inject

class NewsItemViewHolder(
    private val binding: NewsItemBinding,
    private val imageLoader: ImageLoader,
    private val bookmarkButtonListener: BookmarkButtonListener,
    private val shareButtonsListener: ShareButtonsListener,
    private val sourceButtonsListener: SourceButtonsListener,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: NewsItem) {
        setNewsItemComponents(item)
        handleBookmarkIconClick(item)
        handleShareIconClick()
        handleGoToSourceIconClick(item)
    }


    private fun handleBookmarkIconClick(item: NewsItem) {
        binding.icBookmarkItem.setOnClickListener {
            showMessage("handleBookmarkIconClick()")
            bookmarkButtonListener.addBookmark(item)
        }
    }

    private fun handleShareIconClick() {
        binding.icShare.setOnClickListener { showMessage("handleShareIconClick()") }
    }

    private fun handleGoToSourceIconClick(item: NewsItem) {
        binding.icGoToSource.setOnClickListener {
            Log.i(LOG_TAG, "handleGoToSourceIconClick: NewsItemViewHolder articleUrl = ${item.articleUrl}")
            if (item.articleUrl.isNotBlank()) {
                sourceButtonsListener.sourceBtnClicked(item.articleUrl)
            } else {
                showMessage(getMessage(NO_SOURCE_MESSAGE))
            }
        }
    }

    private fun setNewsItemComponents(item: NewsItem) {
        imageLoader.loadImage(item.imageUrl, binding.newsImage, R.drawable.ic_app_icon)

        binding.titleTv.text =
            if (item.title.isNullOrBlank()) getMessage(NO_TITLE_MESSAGE) else item.title
        binding.descriptionTv.text =
            if (item.description.isNullOrBlank()) getMessage(NO_TITLE_DESCRIPTION) else item.description
        binding.sourceTv.text =
            if (item.sourceUrl.isNullOrBlank()) getMessage(NO_SOURCE_MESSAGE) else item.sourceUrl
    }

    private fun getMessage(@StringRes messageId: Int): String {
        return binding.root.context.resources.getString(messageId)
    }

    private fun showMessage(message: String) {
        Toast.makeText(binding.root.context, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val NO_TITLE_MESSAGE = R.string.no_title_message
        const val NO_TITLE_DESCRIPTION = R.string.no_description_message
        const val NO_SOURCE_MESSAGE = R.string.no_source_message
    }

}