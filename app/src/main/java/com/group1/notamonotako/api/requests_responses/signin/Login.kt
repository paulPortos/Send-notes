package com.group1.notamonotako.api.requests_responses.signin


data class Login(
    val email: String,
    val password: String

)
class LoginResponse(
    val token: String,
    val user: User,
    val email: String
)

data class User(
    val id: Int,
    val email: String,
    val username: String
)

data class UserResponse(
    val user: User,
    val email: String,
    val username: String
)

