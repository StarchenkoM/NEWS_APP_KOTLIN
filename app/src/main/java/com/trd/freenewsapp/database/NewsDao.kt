package com.trd.freenewsapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNewsToDb(news: NewsRoomEntity): Long

    @Query("SELECT * FROM News_table")
    fun getAllBookmarksAsList(): List<NewsRoomEntity>

    @Query("DELETE FROM News_table WHERE title =:title")
    fun deleteNewsEntity(title: String): Int

}