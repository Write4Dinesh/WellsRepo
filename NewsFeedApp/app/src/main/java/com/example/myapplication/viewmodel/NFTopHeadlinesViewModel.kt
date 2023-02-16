package com.example.myapplication.viewmodel

import androidx.lifecycle.*
import com.example.myapplication.NFApplication
import com.example.myapplication.model.http.services.topheadlines.NFTopHeadlines
import com.example.myapplication.model.repositories.NFTopHeadlinesRepo
import com.example.myapplication.utils.NFLogger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import rx.Observable
import rx.schedulers.Schedulers

class NFTopHeadlinesViewModel(application: NFApplication, private val headLinesRepo: NFTopHeadlinesRepo) :
    AndroidViewModel(application) {
     val headLinesLiveData: MutableLiveData<NFTopHeadlines> = MutableLiveData()
    fun getHeadLines(country: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val headlines: Observable<NFTopHeadlines> = headLinesRepo.getHeadLines(country)
            NFLogger.log(javaClass.name, headlines.toString())
            headlines.observeOn(Schedulers.io())
                .subscribeOn(Schedulers.computation())
                .subscribe{
                    NFLogger.log(javaClass.name, it.toString())
                    headLinesLiveData.postValue(it)
                }

            //headLinesLiveData.value = headlines.
        }
    }

}