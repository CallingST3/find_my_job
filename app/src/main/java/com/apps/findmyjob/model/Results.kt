package com.apps.findmyjob.model

import com.google.gson.annotations.SerializedName

data class Results(

	@field:SerializedName("vacancies")
	val vacancies: List<VacanciesList?>? = null
)