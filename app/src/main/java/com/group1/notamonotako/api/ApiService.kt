package com.group1.notamonotako.api

import com.group1.notamonotako.api.requests_responses.LoginRequest
import com.group1.notamonotako.api.requests_responses.LoginResponse
import com.group1.notamonotako.api.requests_responses.RegistrationRequest
import com.group1.notamonotako.api.requests_responses.RegistrationResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {

    
    //Unfinished
    @POST("login")
    fun signInUser(@Body request: LoginRequest): Call<LoginResponse>

    @POST("register")
    fun signUpUser(@Body request: RegistrationRequest): Call<RegistrationResponse>

    @POST("logout")
    fun logout(): Call<Unit>

}