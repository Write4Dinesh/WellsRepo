package com.example.myapplication.viewmodel

import androidx.lifecycle.*
import com.example.myapplication.NFApplication
import com.example.myapplication.model.http.services.topheadlines.NFTopHeadlines
import com.example.myapplication.model.repositories.NFTopHeadlinesRepo
import com.example.myapplication.utils.NFLogger
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers

class NFTopHeadlinesViewModel(
    application: NFApplication,
    private val headLinesRepo: NFTopHeadlinesRepo
) :
    AndroidViewModel(application) {
    val headLinesLiveData: MutableLiveData<NFTopHeadlines> = MutableLiveData()
    val isLoadingLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val errorLiveData: MutableLiveData<String> = MutableLiveData()
    fun getHeadLines(country: String) {
        isLoadingLiveData.value = true
        val headlines: Single<NFTopHeadlines> = headLinesRepo.getHeadLines(country)
        NFLogger.log(javaClass.name, headlines.toString())
        headlines
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<NFTopHeadlines>() {
                override fun onSuccess(t: NFTopHeadlines) {
                    isLoadingLiveData.value = false
                    NFLogger.log(javaClass.name, t.toString())
                    headLinesLiveData.postValue(t)
                }

                override fun onError(e: Throwable) {
                    isLoadingLiveData.value = false
                    errorLiveData.value = e.toString()
                    NFLogger.log(javaClass.name, "Error:${e.stackTraceToString()}")
                }
            })

    }


}

