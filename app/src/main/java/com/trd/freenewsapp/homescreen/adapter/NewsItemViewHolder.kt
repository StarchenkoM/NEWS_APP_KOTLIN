package com.trd.freenewsapp.homescreen.adapter

import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.trd.freenewsapp.R
import com.trd.freenewsapp.databinding.NewsItemBinding
import com.trd.freenewsapp.utils.ImageLoader

class NewsItemViewHolder(
    private val binding: NewsItemBinding,
    private val imageLoader: ImageLoader,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: NewsItem) {
        setNewsItemComponents(item)
        handleShareIconClick()
        handleBookmarkIconClick()
        handleGoToSourceIconClick()
    }

    private fun handleGoToSourceIconClick() {
        binding.icGoToSource.setOnClickListener { showMessage("handleGoToSourceIconClick()") }
    }

    private fun handleBookmarkIconClick() {
        binding.icBookmarkItem.setOnClickListener { showMessage("handleBookmarkIconClick()") }
    }

    private fun handleShareIconClick() {
        binding.icShare.setOnClickListener { showMessage("handleShareIconClick()") }
    }

    private fun setNewsItemComponents(item: NewsItem) {
        imageLoader.loadImage(item.imageUrl, binding.newsImage, R.drawable.ic_app_icon)

        val noTitleMessage = binding.root.context.resources.getString(R.string.no_title_message)
        val noDescriptionMessage = binding.root.context.resources.getString(R.string.no_description_message)
        val noSourceMessage = binding.root.context.resources.getString(R.string.no_source_message)

        binding.titleTv.text = if (item.title.isNullOrBlank()) noTitleMessage else item.title
        binding.descriptionTv.text = if (item.description.isNullOrBlank()) noDescriptionMessage else item.description
        binding.sourceTv.text = if (item.sourceUrl.isNullOrBlank()) noSourceMessage else item.sourceUrl
    }


    private fun showMessage(message: String) {
        Toast.makeText(binding.root.context, message, Toast.LENGTH_SHORT).show()
    }


}