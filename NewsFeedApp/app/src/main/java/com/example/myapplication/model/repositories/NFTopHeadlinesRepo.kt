package com.example.myapplication.model.repositories

import com.example.myapplication.model.http.services.topheadlines.NFTopHeadlines
import com.example.myapplication.model.http.services.topheadlines.NFTopHeadlinesService
import com.example.myapplication.utils.NFLogger
import io.reactivex.rxjava3.core.Single
class NFTopHeadlinesRepo(private val headLineService: NFTopHeadlinesService) {
     fun getHeadLines(country: String): Single<NFTopHeadlines> {

        val headLine: Single<NFTopHeadlines> = headLineService.getHeadLines(country)
        NFLogger.log(javaClass.name, headLine.toString())
        return headLine
}

}