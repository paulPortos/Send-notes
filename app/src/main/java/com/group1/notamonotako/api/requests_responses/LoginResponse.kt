package com.group1.notamonotako.api.requests_responses

data class LoginResponse(
    val error: String,
    val message: String,
    val token: String
)
