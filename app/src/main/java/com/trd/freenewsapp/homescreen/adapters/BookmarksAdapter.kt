package com.trd.freenewsapp.homescreen.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.trd.freenewsapp.databinding.NewsItemBinding
import com.trd.freenewsapp.listeners.BookmarkButtonListener
import com.trd.freenewsapp.listeners.SearchNoMatchesListener
import com.trd.freenewsapp.listeners.ShareButtonsListener
import com.trd.freenewsapp.listeners.SourceButtonsListener
import com.trd.freenewsapp.utils.ImageLoader


class BookmarksAdapter(
    private val imageLoader: ImageLoader,
    private val bookmarkButtonListener: BookmarkButtonListener,
    private val shareButtonsListener: ShareButtonsListener,
    private val sourceButtonsListener: SourceButtonsListener,
    private val searchNoMatchesListener: SearchNoMatchesListener,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {
    private var newsItems: List<NewsItem> = listOf()
    private var newsItemsFiltered: List<NewsItem> = listOf()
    private var currentPattern: CharSequence? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = NewsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookmarksViewHolder(
            binding,
            imageLoader,
            bookmarkButtonListener,
            shareButtonsListener,
            sourceButtonsListener
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentItem = newsItemsFiltered[position]
        (holder as BookmarksViewHolder).bind(currentItem)
    }

    override fun getItemCount(): Int = newsItemsFiltered.size

    fun setData(items: List<NewsItem>) {
        newsItems = items.asReversed()
        filter.filter(currentPattern)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(pattern: CharSequence?): FilterResults {
                currentPattern = pattern
                val filterResults = FilterResults()
                filterResults.values = provideFilteredList(pattern)
                return filterResults
            }

            override fun publishResults(p0: CharSequence?, filterResults: FilterResults?) {
                val filteredItems = filterResults?.values as List<NewsItem>
                newsItemsFiltered = filteredItems.toMutableList()
                handleSearchNoMatches(filteredItems)
                notifyDataSetChanged()
            }
        }
    }

    private fun provideFilteredList(pattern: CharSequence?): List<NewsItem> {
        return if (pattern.isNullOrBlank()) {
            newsItems
        } else {
            searchByPattern(pattern)
        }
    }

    private fun searchByPattern(pattern: CharSequence): List<NewsItem> {
        val searchPattern = pattern.toString().lowercase().trim()
        return newsItemsFiltered.filter { it.title.lowercase().contains(searchPattern) }
    }

    private fun handleSearchNoMatches(result: List<NewsItem>) {
        val isMatchesFound = (currentPattern?.isNotEmpty() == true && result.isEmpty())
        searchNoMatchesListener.searchNoMatches(isMatchesFound)
    }

}