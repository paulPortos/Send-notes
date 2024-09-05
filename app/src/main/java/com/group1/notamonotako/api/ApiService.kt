package com.group1.notamonotako.api

import com.group1.notamonotako.api.requests_responses.flashcards.CreateFlashcardsRequest
import com.group1.notamonotako.api.requests_responses.flashcards.CreateFlashcardsResponse
import com.group1.notamonotako.api.requests_responses.signin.LoginRequest
import com.group1.notamonotako.api.requests_responses.signin.LoginResponse
import com.group1.notamonotako.api.requests_responses.signup.RegistrationRequest
import com.group1.notamonotako.api.requests_responses.signup.RegistrationResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {

    
    /*
    Authentication
     */
    @POST("login")
    fun signInUser(@Body request: LoginRequest): Call<LoginResponse>

    @POST("register")
    fun signUpUser(@Body request: RegistrationRequest): Call<RegistrationResponse>

    @POST("logout")
    fun logout(@Header("Authorization") authHeader: String): Call<Unit>

    /*
    Flashcards CRUD
     */

    @POST
    fun createFlashcard(@Header("Authorization") authHeader: String, @Body request: CreateFlashcardsRequest): Call<CreateFlashcardsResponse>
}