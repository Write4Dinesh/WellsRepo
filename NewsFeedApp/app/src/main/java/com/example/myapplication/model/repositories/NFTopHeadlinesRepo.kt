package com.example.myapplication.model.repositories

import com.example.myapplication.model.http.services.topheadlines.NFTopHeadlines
import com.example.myapplication.model.http.services.topheadlines.NFTopHeadlinesService
import com.example.myapplication.utils.NFLogger
import rx.Observable

class NFTopHeadlinesRepo(private val headLineService: NFTopHeadlinesService) {
    suspend fun getHeadLines(country: String): Observable<NFTopHeadlines> {

        val headLine:Observable<NFTopHeadlines> = headLineService.getHeadLines(country)
        NFLogger.log(javaClass.name, headLine.toString())
        return headLine
}

}