package com.emirhanuyunmaz.kotlinfragmentnews.view.service

import com.emirhanuyunmaz.kotlinfragmentnews.view.model.NewsModel
import com.emirhanuyunmaz.kotlinfragmentnews.view.util.Const.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {

    @GET("v2/top-headlines?country=us")
    suspend fun getData(
        @Query("apiKey") apiKey:String=API_KEY
    ) :Response<NewsModel>

    @GET("/v2/everything?sortBy=publishedAt")
    suspend fun getSearchData(
        @Query("q") q:String,
        @Query("apiKey") apiKey: String= API_KEY
    ):Response<NewsModel>
}