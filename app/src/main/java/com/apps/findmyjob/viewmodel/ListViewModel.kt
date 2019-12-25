package com.apps.findmyjob.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.apps.findmyjob.model.ResponseFromEndpoint
import com.apps.findmyjob.model.VacanciesList
import com.apps.findmyjob.repository.ApiService
import com.apps.findmyjob.util.APP_TAG
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers


class ListViewModel(application: Application) : BaseViewModel(application) {

    private val apiService = ApiService()

    val vacancies = MutableLiveData<List<VacanciesList?>>()
    val loadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    private val disposable = CompositeDisposable()

    fun refresh(regionId: String, text: String, offset : String, limit : String, companyCode: String, typeOfRequest : Int) {
        when(typeOfRequest) {
            1 -> {
                fetchCompaniesFromSearchFromRemote(regionId, text, offset, limit)
            }
            2 -> {
                fetchCompaniesByCompanyCodeFromRemote(companyCode)
            }
        }
    }

    private fun fetchCompaniesByCompanyCodeFromRemote(companyCode : String) {
        loading.value = true
        disposable.add(
            apiService.getVacanciesFromCompany(companyCode)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<ResponseFromEndpoint>() {
                    override fun onSuccess(response: ResponseFromEndpoint) {
                        response.results?.vacancies?.let{list ->
                            vacancyRetrieved(list)
                        }
                    }

                    override fun onError(e: Throwable) {
                        loadError.value = true
                        loading.value = false
                        e.printStackTrace()
                    }
                })
        )
    }

    private fun fetchCompaniesFromSearchFromRemote(regionId: String, text: String, offset : String, limit : String) {
        loading.value = true
        disposable.add(
            apiService.getVacancies(regionId, text, offset, limit)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<ResponseFromEndpoint>() {
                    override fun onSuccess(response: ResponseFromEndpoint) {
                        val list = response.results?.vacancies
                        if (list != null) {
                            vacancyRetrieved(list)
                        } else {
                            vacancyEmptyResponseRetrieved()
                        }
                    }

                    override fun onError(e: Throwable) {
                        loadError.value = true
                        loading.value = false
                        e.printStackTrace()
                    }
                })
        )
    }



    private fun vacancyRetrieved(list: List<VacanciesList?>) {
        vacancies.value = list
        loadError.value = false
        loading.value = false
    }

    private fun vacancyEmptyResponseRetrieved() {
        Log.d(APP_TAG, "no vacancies")
        vacancies.value = null
        loadError.value = false
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}