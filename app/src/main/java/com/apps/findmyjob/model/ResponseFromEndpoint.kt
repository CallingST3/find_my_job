package com.apps.findmyjob.model

import com.google.gson.annotations.SerializedName

data class ResponseFromEndpoint(

	@field:SerializedName("request")
	val request: Request? = null,

	@field:SerializedName("meta")
	val meta: Meta? = null,

	@field:SerializedName("results")
	val results: Results? = null,
	
	@field:SerializedName("status")
	val status: String? = null
)