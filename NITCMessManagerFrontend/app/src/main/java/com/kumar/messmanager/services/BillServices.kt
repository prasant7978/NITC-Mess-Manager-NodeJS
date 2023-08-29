package com.kumar.messmanager.services

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PUT
import retrofit2.http.Query

interface BillServices {
    @PUT("messBill/generateBill")
    fun generateBill(@Header("user-auth-token") token: String, @Query("totalDays") totalDays: Int): Call<Boolean>

    @GET("messBill/getMessBill")
    fun getBillDetails(@Header("user-auth-token") token: String): Call<HashMap<String, String>>

    @PUT("messBill/payBill")
    fun payBill(@Header("user-auth-token") token: String, @Query("messBill") messBill: Int, @Query("messName") messName: String): Call<Boolean>
}