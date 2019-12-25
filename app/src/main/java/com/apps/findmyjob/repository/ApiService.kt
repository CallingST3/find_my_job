package com.apps.findmyjob.repository

import com.apps.findmyjob.BuildConfig
import com.apps.findmyjob.model.ResponseFromEndpoint
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ApiService {

    private val BASE_URL = "https://opendata.trudvsem.ru/api/v1/"
    
    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(provideOkHttpClient())
        .build()
        .create(VacanciesApi::class.java)
    
    fun getVacancies(regionId:String, text : String, offset : String, limit : String) : Single<ResponseFromEndpoint> {
        return api.getVacancies(regionId, text, offset, limit)
    }

    fun getVacancyById(companyCode : String, vacancyId : String) : Single<ResponseFromEndpoint> {
        return api.getVacancyVyId(companyCode, vacancyId)
    }

    fun getVacanciesFromCompany(companyCode : String) : Single<ResponseFromEndpoint> {
        return api.getVacanciesFromCompany(companyCode)
    }

    private fun provideLogging() : HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
        return logging
    }

    private fun provideOkHttpClient() : OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(provideLogging())
        return httpClient.build()
    }
}