package com.apps.findmyjob.repository

import com.apps.findmyjob.model.ResponseFromEndpoint
import com.apps.findmyjob.model.Vacancy
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface VacanciesApi {

    //http://opendata.trudvsem.ru/api/v1/vacancies/region/27?text=android

    @GET("vacancies/region/{regionId}")
    fun getVacancies(@Path("regionId") regionId : String,
                     @Query("text") text : String,
                     @Query("offset") offset : String,
                     @Query("limit") limit : String) : Single<ResponseFromEndpoint>

    @GET("vacancies/vacancy/{companyCode}/{vacancyId}")
    fun getVacancyVyId(@Path("companyCode") companyCode : String,
                       @Path("vacancyId") vacancyId : String) : Single<ResponseFromEndpoint>

}