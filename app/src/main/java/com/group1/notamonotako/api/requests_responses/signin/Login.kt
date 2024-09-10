package com.group1.notamonotako.api.requests_responses.signin


data class Login(
    val username: String,
    val password: String

)
class LoginResponse(
    val token: String,
    val user: User
)



data class User(
    val id: Int,
    val username: String
)

