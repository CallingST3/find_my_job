package com.apps.findmyjob.model

import com.google.gson.annotations.SerializedName

data class Meta(

	@field:SerializedName("total")
	val total: Int? = null,

	@field:SerializedName("limit")
	val limit: Int? = null
)