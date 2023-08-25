package com.kumar.messmanager.services

import com.kumar.messmanager.model.Admin
import com.kumar.messmanager.model.Contractor
import com.kumar.messmanager.model.Student
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface ProfileService {
    @GET("getProfile/contractor")
    fun getContractorProfileWithToken(@Header("user-auth-token") token:String): Call<Contractor>

    @GET("getProfile/allContractor")
    fun getAllContractorProfile(@Header("user-auth-token") token: String): Call<ArrayList<Contractor>>

    @PUT("getProfile/updateContractor")
    fun updateContractorProfile(@Body map: HashMap<String, Any>, @Header("user-auth-token") token:String): Call<Boolean>

    @PUT("getProfile/addFeedbackToContractor")
    fun addFeedbackToContractor(@Body map: HashMap<String, String>, @Header("user-auth-token") token: String, @Query("messName") messName: String): Call<Boolean>

    @GET("getProfile/student")
    fun getStudentProfileWithToken(@Header("user-auth-token") token:String): Call<Student>

    @PUT("getProfile/updateStudent")
    fun updateStudentProfile(@Body map: HashMap<String, Any>, @Header("user-auth-token") token:String): Call<Boolean>

    @POST("getProfile/addMessNameToStudentProfile")
    fun addMessNameToStudentProfile(@Query("messName") messName: String, @Header("user-auth-token") token: String): Call<Boolean>

    @GET("getProfile/admin")
    fun getAdminProfileWithToken(@Header("user-auth-token") token:String): Call<Admin>
}