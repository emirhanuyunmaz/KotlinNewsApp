package com.emirhanuyunmaz.kotlinfragmentnews.view.model

data class NewsModel(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)