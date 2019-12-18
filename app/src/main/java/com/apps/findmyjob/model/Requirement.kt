package com.apps.findmyjob.model

import com.google.gson.annotations.SerializedName

data class Requirement(

	@field:SerializedName("qualification")
	val qualification: String? = null,

	@field:SerializedName("experience")
	val experience: String? = null,

	@field:SerializedName("education")
	val education: String? = null
)