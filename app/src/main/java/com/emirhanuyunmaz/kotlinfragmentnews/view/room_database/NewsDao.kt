package com.emirhanuyunmaz.kotlinfragmentnews.view.room_database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.emirhanuyunmaz.kotlinfragmentnews.view.util.Resource

@Dao
interface NewsDao {

    @Query("SELECT * FROM NewsSaveTable")
    fun getAllData():List<NewsData>

    @Delete
    fun delete(newsData: NewsData)

    @Insert
    fun insert(vararg newsData: NewsData)
}