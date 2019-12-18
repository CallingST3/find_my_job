package com.apps.findmyjob.model

import com.google.gson.annotations.SerializedName

data class AddressItem(

	@field:SerializedName("lng")
	val lng: String? = null,

	@field:SerializedName("location")
	val location: String? = null,

	@field:SerializedName("lat")
	val lat: String? = null
)