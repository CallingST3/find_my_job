package com.apps.findmyjob.viewmodel

import android.app.Application
import android.text.Html
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.apps.findmyjob.model.ResponseFromEndpoint
import com.apps.findmyjob.repository.ApiService
import com.apps.findmyjob.util.APP_TAG
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class DetailViewModel(application: Application) : BaseViewModel(application) {

    val vacancy = MutableLiveData<ResponseFromEndpoint>()
    val loadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    private val apiService = ApiService()

    private val disposable = CompositeDisposable()

    fun refresh(vacancyId : String, companyCode : String) {
        fetchFromRemote(vacancyId, companyCode)
    }

    private fun fetchFromRemote(vacancyId : String, companyCode : String) {
        loading.value = true
        disposable.add(
            apiService.getVacancyById(companyCode, vacancyId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<ResponseFromEndpoint>() {
                    override fun onSuccess(responseFromEndpoint: ResponseFromEndpoint) {
                        vacancyRetrieved(responseFromEndpoint)
                        Log.d(APP_TAG, "success! ${responseFromEndpoint.results?.vacancies?.get(0)?.vacancy?.jobName}")
                    }
                    override fun onError(e: Throwable) {
                        loadError.value = true
                        loading.value = false
                        Log.e(APP_TAG, "onError ${e.localizedMessage}")

                    }
                })
        )
    }

    private fun vacancyRetrieved(vacancyFromEndpoint : ResponseFromEndpoint) {
        vacancy.value = vacancyFromEndpoint
        loading.value = false
        loadError.value = false
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}