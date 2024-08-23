package com.group1.notamonotako.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    //@POST("login")
    //suspend fun loginUser(@Body request: LoginRequest): Response<LoginResponse>

    @POST("register")
    fun registerUser(@Body request: RegistrationRequest): Call<RegistrationResponse>
}