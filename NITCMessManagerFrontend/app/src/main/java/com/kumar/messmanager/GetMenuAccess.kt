package com.kumar.messmanager

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.kumar.messmanager.model.Menu
import com.kumar.messmanager.services.MessMenuServices
import com.kumar.messmanager.services.ServiceBuilder
import com.kumar.messmanager.viewmodels.SharedViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class GetMenuAccess(private val parentFragment: Fragment) {
    suspend fun retrieveMessMenu(messName: String, sharedViewModel: SharedViewModel, selectedDay: String): Menu?{
        return suspendCoroutine { continuation ->
            val menuServices: MessMenuServices = ServiceBuilder.buildService(MessMenuServices::class.java)
            val requestCall = menuServices.getMessMenu(sharedViewModel.token, messName, selectedDay)

            requestCall.enqueue(object: Callback<Menu?> {
                override fun onResponse(call: Call<Menu?>, response: Response<Menu?>
                ) {
                    if(response.isSuccessful){
                        if(response.body() != null){
                            val menu = response.body()!!
                            continuation.resume(menu)
                        }
                        else{
                            Toast.makeText(parentFragment.activity,"No menu have been added for $selectedDay", Toast.LENGTH_SHORT).show()
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

                override fun onFailure(call: Call<Menu?>, t: Throwable) {
                    Toast.makeText(parentFragment.activity, t.localizedMessage, Toast.LENGTH_SHORT).show()
                    continuation.resume(null)
                }

            })
        }
    }
}