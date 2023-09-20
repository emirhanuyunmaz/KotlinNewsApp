package com.emirhanuyunmaz.kotlinfragmentnews.view.repository

import com.emirhanuyunmaz.kotlinfragmentnews.view.model.NewsModel
import com.emirhanuyunmaz.kotlinfragmentnews.view.util.Resource

interface NewsDownload {
    suspend fun downloadNews():Resource<NewsModel>
}