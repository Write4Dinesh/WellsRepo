package com.example.myapplication.model.http.services.topheadlines

import com.example.myapplication.model.http.NFRestClient
import com.example.myapplication.utils.NFLogger
import io.reactivex.rxjava3.core.Single
class NFTopHeadlinesService {
    fun getHeadLines(country: String): Single<NFTopHeadlines> {
        val headLines: Single<NFTopHeadlines> = NFRestClient.getEndpointImplementation().getHeadLines(country)
        NFLogger.log(javaClass.name, "Got headlines $headLines")
        return headLines
    }

}