package com.apps.findmyjob.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.apps.findmyjob.model.ResponseFromEndpoint
import com.apps.findmyjob.model.VacanciesList
import com.apps.findmyjob.repository.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class ListByCompanyCodeViewModel(application: Application) : AndroidViewModel(application) {

    private val apiService = ApiService()

    val vacancies = MutableLiveData<List<VacanciesList?>>()
    val loadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    private val disposable = CompositeDisposable()

    fun refresh(companyCode : String) {
        fetchFromRemote(companyCode)
    }

    private fun fetchFromRemote(companyCode : String) {
        loading.value = true
        disposable.add(
            apiService.getVacanciesFromCompany(companyCode)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<ResponseFromEndpoint>() {
                    override fun onSuccess(response: ResponseFromEndpoint) {
                        vacanciesRetrieved(response)
                    }

                    override fun onError(e: Throwable) {
                        loadError.value = true
                        loading.value = false
                        e.printStackTrace()
                    }
                })
        )
    }

    private fun vacanciesRetrieved(response: ResponseFromEndpoint) {
        vacancies.value = response.results?.vacancies
        loadError.value = false
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}