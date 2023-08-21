package com.kumar.messmanager.student.access

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.kumar.messmanager.model.Student
import com.kumar.messmanager.services.ProfileService
import com.kumar.messmanager.services.ServiceBuilder
import com.kumar.messmanager.viewmodels.SharedViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class GetProfileAccess(
    private val parentFragment: Fragment
) {
    suspend fun retrieveStudentInfo(sharedViewModel: SharedViewModel): Student?{
        return suspendCoroutine { continuation ->
            val sharedPreferences = parentFragment.activity?.getSharedPreferences("saveToken", Context.MODE_PRIVATE)
            val token = sharedPreferences?.getString("token", "")

            val profileService: ProfileService = ServiceBuilder.buildService(ProfileService::class.java)
            val requestCall = profileService.getStudentProfileWithToken(token.toString())

            requestCall.enqueue(object: Callback<Student>{
                override fun onResponse(call: Call<Student>, response: Response<Student>) {
                    if(response.isSuccessful){
                        if(response.body() != null){
                            sharedViewModel.student = response.body()!!
                            sharedViewModel.userType = response.body()!!.userType
                            sharedViewModel.token = token.toString()

                            continuation.resume(response.body()!!)
                        }else {
                            Toast.makeText(
                                parentFragment.activity,
                                "Student not found...",
                                Toast.LENGTH_SHORT
                            ).show()
                            continuation.resume(null)
                        }
                    }
                    else{
                        Toast.makeText(
                            parentFragment.activity,
                            "Some error occurred",
                            Toast.LENGTH_SHORT
                        ).show()
                        continuation.resume(null)
                    }
                }

                override fun onFailure(call: Call<Student>, t: Throwable) {
                    Toast.makeText(
                        parentFragment.activity,
                        "Server Error",
                        Toast.LENGTH_SHORT
                    ).show()
                    continuation.resume(null)
                }

            })
        }
    }
}