package com.emirhanuyunmaz.kotlinfragmentnews.view.repository

import com.emirhanuyunmaz.kotlinfragmentnews.view.model.NewsModel
import com.emirhanuyunmaz.kotlinfragmentnews.view.service.NewsService
import com.emirhanuyunmaz.kotlinfragmentnews.view.util.Resource
import org.koin.java.KoinJavaComponent.inject

class NewsDownloadImpl(private val  api:NewsService):NewsDownload {

    override suspend fun downloadNews(): Resource<NewsModel> {
        val response=api.getData()
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