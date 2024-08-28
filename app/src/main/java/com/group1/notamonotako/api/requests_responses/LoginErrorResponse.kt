package com.group1.notamonotako.api.requests_responses

import com.google.gson.annotations.SerializedName

data class LoginErrorResponse(

	@field:SerializedName("error")
	val error: String? = null
)
