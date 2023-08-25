package com.kumar.messmanager.services

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.PUT
import retrofit2.http.Query

interface FeedbackServices {
    @PUT("feedback/addFeedback")
    fun addFeedback(@Body map: HashMap<String, String>, @Header("user-auth-token") token: String, @Query("messName") messName: String): Call<Boolean>
}