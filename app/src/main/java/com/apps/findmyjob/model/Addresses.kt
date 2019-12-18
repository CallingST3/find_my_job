package com.apps.findmyjob.model

import com.google.gson.annotations.SerializedName

data class Addresses(

	@field:SerializedName("address")
	val address: List<AddressItem?>? = null
)