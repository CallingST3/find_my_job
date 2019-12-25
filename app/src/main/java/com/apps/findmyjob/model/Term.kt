package com.apps.findmyjob.model

import com.google.gson.annotations.SerializedName

data class Term(
    @field:SerializedName("text")
    val term: String? = null
)