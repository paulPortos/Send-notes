package com.group1.notamonotako.api

import com.group1.notamonotako.api.requests_responses.signin.LoginRequest
import com.group1.notamonotako.api.requests_responses.signin.LoginResponse
import com.group1.notamonotako.api.requests_responses.signup.RegistrationRequest
import com.group1.notamonotako.api.requests_responses.signup.RegistrationResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    
    //Unfinished
    @POST("login")
    fun signInUser(@Body request: LoginRequest): Call<LoginResponse>

    @POST("register")
    fun signUpUser(@Body request: RegistrationRequest): Call<RegistrationResponse>

}