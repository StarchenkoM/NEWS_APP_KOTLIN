package com.trd.freenewsapp.homescreen.adapters

import android.content.Context
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
    private val context: Context,
    private val imageLoader: ImageLoader,
    private val bookmarkButtonListener: BookmarkButtonListener,
    private val shareButtonsListener: ShareButtonsListener,
    private val sourceButtonsListener: SourceButtonsListener,
    private val searchNoMatchesListener: SearchNoMatchesListener,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {
    private var newsItems: MutableList<NewsItem> = mutableListOf()
    private var newsItemsFiltered: MutableList<NewsItem> = mutableListOf()


    private var items: MutableList<NewsItem> = mutableListOf()
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
        val currentItem = newsItems[position]
        (holder as BookmarksViewHolder).bind(currentItem)
    }

    override fun getItemCount(): Int = newsItems.size

    fun setData(items: List<NewsItem>) {
        newsItems = items.toMutableList()
        newsItemsFiltered.addAll(items)
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(pattern: CharSequence?): FilterResults {
                currentPattern = pattern
                val filteredList = mutableListOf<NewsItem>()
                if (pattern.isNullOrBlank()) {
                    filteredList.addAll(newsItemsFiltered)
                } else {
                    val searchPattern = pattern.toString().lowercase().trim()
                    val filtered =
                        newsItemsFiltered.filter { it.title.lowercase().contains(searchPattern) }
                    filteredList.addAll(filtered)
                }

                val filterResults = FilterResults()
                filterResults.values = filteredList
                return filterResults
            }

            override fun publishResults(p0: CharSequence?, filterResults: FilterResults?) {
                val filteredItems = filterResults?.values as List<NewsItem>
                newsItems.clear()
                newsItems.addAll(filteredItems)
                handleSearchNoMatches(filteredItems)
                notifyDataSetChanged()
            }

        }
    }

    private fun handleSearchNoMatches(result: List<NewsItem>) {
        val isMatchesFound = (currentPattern?.isNotEmpty() == true && result.isEmpty() )
        searchNoMatchesListener.searchNoMatches(isMatchesFound)
    }

}