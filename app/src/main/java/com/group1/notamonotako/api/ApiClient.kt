package com.group1.notamonotako.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    /*
    Change it to your ip address
    Ex:
    private const val BASE_URL = "http://YOUR_IP_ADDRESS:8000/api/"
     */
    private const val BASE_URL = "http://192.168.1.6:8000/api/"

    private val logging = HttpLoggingInterceptor().apply {
        setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}



// Android token get (GPT)
/*

import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL = "http://192.168.0.102:8000/api/"

    private val logging = HttpLoggingInterceptor().apply {
        setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .addInterceptor { chain ->
            val requestBuilder = chain.request().newBuilder()
            val token = getAuthToken(appContext)  // Use the passed context
            if (token != null) {
                requestBuilder.addHeader("Authorization", "Bearer $token")
            }
            chain.proceed(requestBuilder.build())
        }
        .build()

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private fun getAuthToken(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("auth_token", null)
    }
}

 */