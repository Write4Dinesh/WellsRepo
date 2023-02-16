package com.example.myapplication.model.http.services.topheadlines

data class NFArticlesItem(
    val publishedAt: String = "",
    val author: String = "",
    val urlToImage: String = "",
    val description: String = "",
    val source: NFSource,
    val title: String = "",
    val url: String = "",
    val content: String = ""
)