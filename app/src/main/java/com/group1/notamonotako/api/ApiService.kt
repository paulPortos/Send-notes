package com.group1.notamonotako.api

import com.group1.notamonotako.api.requests_responses.RegistrationRequest
import com.group1.notamonotako.api.requests_responses.RegistrationResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    
    //Unfinished
    @POST("login")
    fun signInUser(@Body request: RegistrationRequest): Call<RegistrationResponse>

    @POST("register")
    fun signUpUser(@Body request: RegistrationRequest): Call<RegistrationResponse>

}