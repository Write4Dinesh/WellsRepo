package com.example.myapplication.model.http

import com.example.myapplication.model.http.services.topheadlines.NFTopHeadlines
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable


interface NFEndpointInterface {

    @GET("top-headlines")
    fun getHeadLines(@Query("Country") country: String): Observable<NFTopHeadlines>
}