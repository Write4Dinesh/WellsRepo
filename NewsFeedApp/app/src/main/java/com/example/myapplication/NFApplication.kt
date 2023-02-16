package com.example.myapplication

import android.app.Application
import com.example.myapplication.model.repositories.NFTopHeadlinesRepo
import com.example.myapplication.model.http.services.topheadlines.NFTopHeadlinesService

class NFApplication : Application() {
    lateinit var topHeadlinesRepo: NFTopHeadlinesRepo
    override fun onCreate() {
        topHeadlinesRepo = NFTopHeadlinesRepo(NFTopHeadlinesService())
        super.onCreate()
    }
}