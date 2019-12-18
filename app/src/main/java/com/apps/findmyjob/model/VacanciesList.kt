package com.apps.findmyjob.model

import com.google.gson.annotations.SerializedName

data class VacanciesList(

	@field:SerializedName("vacancy")
	val vacancy: Vacancy? = null
)