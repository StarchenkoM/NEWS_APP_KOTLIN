package com.trd.freenewsapp.homescreen.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.trd.freenewsapp.databinding.NewsItemBinding
import com.trd.freenewsapp.listeners.BookmarkButtonListener
import com.trd.freenewsapp.listeners.ShareButtonsListener
import com.trd.freenewsapp.listeners.SourceButtonsListener
import com.trd.freenewsapp.utils.ImageLoader


class BookmarksAdapter(
    private val context: Context,
    private val imageLoader: ImageLoader,
    private val bookmarkButtonListener: BookmarkButtonListener,
    private val shareButtonsListener: ShareButtonsListener,
    private val sourceButtonsListener: SourceButtonsListener,
    ) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var newsItems: List<NewsItem> = listOf()
    private var items: MutableList<NewsItem> = mutableListOf()
    private var _currentPattern: CharSequence? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = NewsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookmarksViewHolder(binding, imageLoader,bookmarkButtonListener,shareButtonsListener, sourceButtonsListener)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentItem = newsItems[position]
        (holder as BookmarksViewHolder).bind(currentItem)
    }

    override fun getItemCount(): Int = newsItems.size

    fun setData(items: List<NewsItem>) {
        newsItems = items
        notifyDataSetChanged()
//        this.items = items as MutableList<NewsItem>
    }

}