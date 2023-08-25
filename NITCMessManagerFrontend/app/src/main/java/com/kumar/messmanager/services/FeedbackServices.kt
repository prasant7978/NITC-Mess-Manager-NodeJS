package com.kumar.messmanager.services

import com.kumar.messmanager.model.Feedback
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PUT
import retrofit2.http.Query

interface FeedbackServices {
    @PUT("feedback/addFeedback")
    fun addFeedback(@Body map: HashMap<String, String>, @Header("user-auth-token") token: String, @Query("messName") messName: String): Call<Boolean>

    @GET("feedback/getAllFeedbacks")
    fun getAllFeedbacks(@Header("user-auth-token") token: String): Call<ArrayList<Feedback>>

    @DELETE("feedback/deleteFeedback")
    fun deleteFeedback(@Header("user-auth-token") token: String, @Query("feedbackId") feedbackId: String): Call<Boolean>
}