package com.example.disastermanagementapp.data.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

data class GoogleTokenRequest(val idToken: String)
data class AuthRequest(val email: String, val password: String)
data class AuthResponse(val token: String)

interface AuthApi {
    @POST("api/v1/auth/google")
    suspend fun googleAuth(@Body request: GoogleTokenRequest): Response<AuthResponse>

    @POST("api/v1/auth/authenticate")
    suspend fun login(@Body request: AuthRequest): Response<AuthResponse>

    @POST("api/v1/auth/register")
    suspend fun register(@Body request: AuthRequest): Response<AuthResponse>
}

object RetrofitInstance {
    private const val BASE_URL = "http://192.168.1.38:8080/"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    val api: AuthApi by lazy {
        retrofit.create(AuthApi::class.java)
    }
}
