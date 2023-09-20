package com.emirhanuyunmaz.kotlinfragmentnews.view.repository

import com.emirhanuyunmaz.kotlinfragmentnews.view.model.NewsModel
import com.emirhanuyunmaz.kotlinfragmentnews.view.service.NewsService
import com.emirhanuyunmaz.kotlinfragmentnews.view.util.Resource

class NewsSearchDownloadImpl(private val api:NewsService):NewsSearchDownload {
    override suspend fun downloadSearchNews(searchString: String): Resource<NewsModel> {
        val response=api.getSearchData(searchString)
        return try {
            if (response.isSuccessful){
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("ERROR",null)
            }else{
                Resource.error("ERROR",null)
            }
        }catch (e:Exception){
            Resource.error("ERROR",null)
        }
    }
}