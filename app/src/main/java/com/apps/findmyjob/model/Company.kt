package com.apps.findmyjob.model

import com.google.gson.annotations.SerializedName

data class Company(

	@field:SerializedName("ogrn")
	val ogrn: String? = null,

	@field:SerializedName("companycode")
	val companycode: String? = null,

	@field:SerializedName("hr-agency")
	val hrAgency: Boolean? = null,

	@field:SerializedName("inn")
	val inn: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("logo")
	val logo: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("url")
	val url: String? = null,

	@field:SerializedName("phone")
	val phone: String? = null
)