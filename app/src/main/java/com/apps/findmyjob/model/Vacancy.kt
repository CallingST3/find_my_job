package com.apps.findmyjob.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.apps.findmyjob.util.FULL_DATA_FORMAT_PATTERN
import com.google.gson.annotations.SerializedName
import org.jsoup.Jsoup
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

data class Vacancy(

	@field:SerializedName("addresses")
	val addresses: Addresses? = null,

	@field:SerializedName("job-name")
	val jobName: String? = null,

	@field:SerializedName("salary_max")
	val salaryMax: Int? = null,

	@field:SerializedName("source")
	val source: String? = null,

	@field:SerializedName("employment")
	val employment: String? = null,

	@field:SerializedName("requirement")
	val requirement: Requirement? = null,

	@field:SerializedName("modify-date")
	val modifyDate: String? = null,

	@field:SerializedName("salary")
	val salary: String? = null,

	@field:SerializedName("vac_url")
	val vacUrl: String? = null,

	@field:SerializedName("schedule")
	val schedule: String? = null,

	@field:SerializedName("salary_min")
	val salaryMin: Int? = null,

	@field:SerializedName("creation-date")
	val creationDate: String? = null,

	@field:SerializedName("duty")
	val duty: String? = null,

	@field:SerializedName("company")
	val company: Company? = null,

	@field:SerializedName("currency")
	val currency: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("region")
	val region: Region? = null,

	@field:SerializedName("category")
	val category: Category? = null,

	@field:SerializedName("term")
	val term: Term? = null
) {
	@PrimaryKey(autoGenerate = true)
	var uuid : Int = 0

	fun clearFromHtml(context : String?) : String? {
		context?.let {
			return Jsoup.parse(context).text()
		}
		return context
	}



	fun formatEducationExperience(experience : String) : String {
		return when(experience) {
			"1" -> "$experience год"
			"2", "3", "4" -> "$experience года"
			else -> "$experience лет"
		}
	}

	fun returnIfNotNull(string : String?) : String {
		string?.let {
			return string
		}
		return "не указано"
	}

}