package com.trd.freenewsapp.database

import androidx.room.*

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNewsToDb(news: NewsRoomEntity)

    @Query("SELECT * FROM News_table WHERE title = :newsId ")
    fun getNewsById(newsId: String): NewsRoomEntity?

    @Query("DELETE FROM News_table WHERE title =:newsId")
    fun deleteNewsEntity(newsId: String)

}