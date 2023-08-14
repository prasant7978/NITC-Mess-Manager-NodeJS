package com.kumar.messmanager.services

import com.kumar.messmanager.model.Admin
import com.kumar.messmanager.model.Contractor
import com.kumar.messmanager.model.Student
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface GetProfileService {
    @GET("getProfile/student")
    fun getStudentProfileWithToken(@Header("user-auth-token") token:String): Call<Student>

    @GET("getProfile/contractor")
    fun getContractorProfileWithToken(@Header("user-auth-token") token:String): Call<Contractor>

    @GET("getProfile/admin")
    fun getAdminProfileWithToken(@Header("user-auth-token") token:String): Call<Admin>
}