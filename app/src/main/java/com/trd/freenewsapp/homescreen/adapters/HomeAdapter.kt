package com.trd.freenewsapp.homescreen.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.trd.freenewsapp.databinding.NewsItemBinding
import com.trd.freenewsapp.utils.ImageLoader

const val ITEM_TYPE_NEWS = 1

class HomeAdapter(private val context: Context, private val imageLoader: ImageLoader) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var newsItems: List<NewsItem> = listOf()
    private var items: MutableList<NewsItem> = mutableListOf()
    private var _currentPattern: CharSequence? = null



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_TYPE_NEWS -> {
                val binding = NewsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                NewsItemViewHolder(binding, imageLoader,)
            }
            else -> throw IllegalStateException()
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentItem = newsItems[position]
        (holder as NewsItemViewHolder).bind(currentItem)
    }

    override fun getItemViewType(position: Int): Int = ITEM_TYPE_NEWS

    override fun getItemCount(): Int = newsItems.size

    fun setData(items: List<NewsItem>) {
        newsItems = items
        notifyDataSetChanged()
//        this.items = items as MutableList<NewsItem>
    }

}