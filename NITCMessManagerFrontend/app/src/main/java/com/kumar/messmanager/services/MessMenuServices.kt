package com.kumar.messmanager.services

import com.kumar.messmanager.model.Menu
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface MessMenuServices {
    @GET("messMenu/getMessMenu")
    fun getMessMenu(@Header("user-auth-token") token: String, @Query("messName") messName: String, @Query("day") day: String): Call<Menu?>

    @POST("messMenu/updateMessMenu")
    fun updateMenu(@Body map: HashMap<String, String>, @Header("user-auth-token") token: String): Call<Boolean>
}