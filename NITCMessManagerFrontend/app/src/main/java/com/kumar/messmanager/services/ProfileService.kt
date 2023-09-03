package com.kumar.messmanager.services

import com.kumar.messmanager.model.Admin
import com.kumar.messmanager.model.Contractor
import com.kumar.messmanager.model.Student
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface ProfileService {
//    Contractor
    @GET("getProfile/contractor")
    fun getContractorProfileWithToken(@Header("user-auth-token") token:String): Call<Contractor>

    @GET("getProfile/contractorWithId")
    fun getContractorProfileWithId(@Header("user-auth-token") token:String, @Query("contractorId") contractorId: String): Call<Contractor>

    @GET("getProfile/allContractor")
    fun getAllContractorProfile(@Header("user-auth-token") token: String): Call<ArrayList<Contractor>>

    @PUT("getProfile/updateContractor")
    fun updateContractorProfile(@Body map: HashMap<String, Any>, @Header("user-auth-token") token:String): Call<Boolean>

    @PUT("getProfile/adminUpdateContractor")
    fun adminUpdateContractorProfile(@Body map: HashMap<String, Any>, @Header("user-auth-token") token:String): Call<Boolean>

    @POST("getProfile/addContractor")
    fun addContractor(@Header("user-auth-token") token: String, @Body map: HashMap<String, Any>): Call<Boolean>

    @DELETE("getProfile/deleteContractor")
    fun deleteContractor(@Header("user-auth-token") token: String, @Query("contractorId") contractorId: String): Call<Boolean>

//    Student
    @GET("getProfile/student")
    fun getStudentProfileWithToken(@Header("user-auth-token") token:String): Call<Student>

    @GET("getProfile/studentWithId")
    fun getStudentProfileWithId(@Header("user-auth-token") token:String, @Query("studentId") studentId: String): Call<Student>

    @GET("getProfile/allEnrolledStudent")
    fun getAllEnrolledStudent(@Header("user-auth-token") token:String, @Query("messName") messName: String): Call<ArrayList<Student>>

    @GET("getProfile/allStudents")
    fun getAllStudents(@Header("user-auth-token") token:String): Call<ArrayList<Student>>

    @POST("getProfile/addStudent")
    fun addStudent(@Header("user-auth-token") token: String, @Body map: HashMap<String, String>): Call<Boolean>

    @PUT("getProfile/updateStudent")
    fun updateStudentProfile(@Header("user-auth-token") token:String, @Body map: HashMap<String, Any>): Call<Boolean>

    @DELETE("getProfile/deleteStudent")
    fun deleteStudent(@Header("user-auth-token") token: String, @Query("studentId") studentId: String): Call<Boolean>

    @POST("getProfile/addMessNameToStudentProfile")
    fun addMessNameToStudentProfile(@Query("messName") messName: String, @Header("user-auth-token") token: String): Call<Boolean>

//    Admin
    @GET("getProfile/admin")
    fun getAdminProfileWithToken(@Header("user-auth-token") token:String): Call<Admin>
}