package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.NFApplication
import com.example.myapplication.model.repositories.NFTopHeadlinesRepo

class NFViewModelFactory(private val app: NFApplication, private val repo: NFTopHeadlinesRepo) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NFTopHeadlinesViewModel(app, repo) as T
    }
}
