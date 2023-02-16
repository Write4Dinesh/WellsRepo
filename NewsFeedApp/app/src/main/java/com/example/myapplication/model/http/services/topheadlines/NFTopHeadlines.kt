package com.example.myapplication.model.http.services.topheadlines

data class NFTopHeadlines(val totalResults: Int = 0,
                          val articles: List<NFArticlesItem>?,
                          val status: String = "")