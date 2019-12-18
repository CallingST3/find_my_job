package com.apps.findmyjob.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.apps.findmyjob.model.ResponseFromEndpoint
import com.apps.findmyjob.repository.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers


class ListViewModel(application: Application) : BaseViewModel(application) {

    private val apiService = ApiService()

    val vacancies = MutableLiveData<ResponseFromEndpoint>()
    val loadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    private val disposable = CompositeDisposable()

    fun refresh(regionId: String, text: String, offset : String, limit : String) {
        fetchFromRemote(regionId, text, offset, limit)
    }

    private fun fetchFromRemote(regionId: String, text: String, offset : String, limit : String) {
        loading.value = true
        disposable.add(
            apiService.getVacancies(regionId, text, offset, limit)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<ResponseFromEndpoint>() {
                    override fun onSuccess(response: ResponseFromEndpoint) {
                        vacancyRetrieved(response)
                    }

                    override fun onError(e: Throwable) {
                        loadError.value = true
                        loading.value = false
                        e.printStackTrace()
                    }
                })
        )
    }

    private fun vacancyRetrieved(response: ResponseFromEndpoint) {
        vacancies.value = response
        loadError.value = false
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}