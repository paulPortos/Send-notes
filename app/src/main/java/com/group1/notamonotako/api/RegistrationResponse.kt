package com.group1.notamonotako.api

import com.group1.notamonotako.model.User

data class RegistrationResponse (
    val message: String, val user: User, val token: String
)