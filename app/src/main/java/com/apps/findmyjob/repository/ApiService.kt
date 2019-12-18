package com.apps.findmyjob.repository

import android.util.Log
import com.apps.findmyjob.BuildConfig
import com.apps.findmyjob.model.ResponseFromEndpoint
import com.apps.findmyjob.model.Vacancy
import com.apps.findmyjob.util.APP_TAG
import com.google.gson.GsonBuilder
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ApiService {

    private val BASE_URL = "https://opendata.trudvsem.ru/api/v1/"
    
    private val gsonBuilder = GsonBuilder()
        .excludeFieldsWithoutExposeAnnotation().create()
    
    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(provideOkHttpClient())
        .build()
        .create(VacanciesApi::class.java)
    
    fun getVacancies(regionId:String, text : String, offset : String, limit : String) : Single<ResponseFromEndpoint> {
        Log.d(APP_TAG, "offset=$offset, linit=$limit")
        return api.getVacancies(regionId, text, offset, limit)
    }

    fun getVacancyById(companyCode : String, vacancyId : String) : Single<ResponseFromEndpoint> {
        return api.getVacancyVyId(companyCode, vacancyId)
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