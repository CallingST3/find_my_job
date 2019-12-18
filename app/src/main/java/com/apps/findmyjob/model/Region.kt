package com.apps.findmyjob.model

import com.google.gson.annotations.SerializedName

data class Region(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("region_code")
	val regionCode: String? = null
)