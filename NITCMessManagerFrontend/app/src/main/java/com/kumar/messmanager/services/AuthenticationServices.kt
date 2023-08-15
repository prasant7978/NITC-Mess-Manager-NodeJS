package com.kumar.messmanager.services

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthenticationServices {
    @POST("/auth/signup/student")
    fun singnupStudent(@Body map: HashMap<String, String>): Call<Boolean>

    @POST("/auth/login/student")
    fun loginStudent(@Body map: HashMap<String, String>): Call<Boolean>

    @POST("/auth/login/contractor")
    fun loginContractor(@Body map: HashMap<String, String>): Call<Boolean>

    @POST("/auth/login/admin")
    fun loginAdmin(@Body map: HashMap<String, String>): Call<Boolean>

    @GET("/auth/verifyToken")
    fun validateToken(@Header("user-auth-token") token: String): Call<Boolean>
}