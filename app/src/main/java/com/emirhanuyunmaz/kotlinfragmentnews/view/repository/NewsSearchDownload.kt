package com.emirhanuyunmaz.kotlinfragmentnews.view.repository

import com.emirhanuyunmaz.kotlinfragmentnews.view.model.NewsModel
import com.emirhanuyunmaz.kotlinfragmentnews.view.util.Resource

interface NewsSearchDownload {
    suspend fun downloadSearchNews(searchString: String): Resource<NewsModel>

}