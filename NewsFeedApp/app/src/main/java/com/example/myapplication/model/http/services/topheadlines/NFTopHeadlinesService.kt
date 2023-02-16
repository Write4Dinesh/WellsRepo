package com.example.myapplication.model.http.services.topheadlines

import com.example.myapplication.model.http.NFRestClient
import com.example.myapplication.utils.NFLogger
import rx.Observable

class NFTopHeadlinesService {
    fun getHeadLines(country: String): Observable<NFTopHeadlines> {
        val headLines: Observable<NFTopHeadlines> = NFRestClient.getEndpointImplementation().getHeadLines(country)
        NFLogger.log(javaClass.name, "Got headlines $headLines")
        return headLines
    }

}