package com.group1.notamonotako.api

import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClientTokenWithBT {
    private const val BASE_URL = "http://192.168.0.104:8000/api/"

    private fun createOkHttpClient(token: String?): OkHttpClient {
        val builder = OkHttpClient.Builder()

        // Add an interceptor to add the token to the request headers
        builder.addInterceptor { chain ->
            val originalRequest: Request = chain.request()
            val requestBuilder: Request.Builder = originalRequest.newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .method(originalRequest.method, originalRequest.body)
            val request: Request = requestBuilder.build()
            chain.proceed(request)
        }

        return builder.build()
    }

    fun createRetrofitWithToken(token: String?): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(createOkHttpClient(token))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}