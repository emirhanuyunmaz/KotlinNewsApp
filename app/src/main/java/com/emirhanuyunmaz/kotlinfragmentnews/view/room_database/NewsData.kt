package com.emirhanuyunmaz.kotlinfragmentnews.view.room_database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "NewsSaveTable")
data class NewsData(

    val url:String,
    val imgUrl:String,
    val title:String,
    val source:String,
    val date:String,
){
    @PrimaryKey(autoGenerate = true)
    var id:Int=0
}
