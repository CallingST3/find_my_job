package com.apps.findmyjob.model

import com.google.gson.annotations.SerializedName

data class Category(

	@field:SerializedName("specialisation")
	val specialisation: String? = null
)