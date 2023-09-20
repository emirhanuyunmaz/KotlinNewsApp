package com.emirhanuyunmaz.kotlinfragmentnews.view.room_database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [NewsData::class], version = 1)
abstract class NewsDatabase() :RoomDatabase(){
    abstract fun getDao():NewsDao
}