package com.kumar.messmanager.contractor

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.kumar.messmanager.databinding.FragmentTotalBillGeneratedBinding
import com.kumar.messmanager.services.BillServices
import com.kumar.messmanager.services.ServiceBuilder
import com.kumar.messmanager.viewmodels.SharedViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TotalBillGeneratedFragment : Fragment() {

    private lateinit var totalBillGeneratedBinding: FragmentTotalBillGeneratedBinding
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        totalBillGeneratedBinding = FragmentTotalBillGeneratedBinding.inflate(inflater,container,false)

        val billServices: BillServices = ServiceBuilder.buildService(BillServices::class.java)
        val requestCall = billServices.getBillDetails(sharedViewModel.token)

        requestCall.enqueue(object: Callback<HashMap<String, String>>{
            override fun onResponse(call: Call<HashMap<String, String>>, response: Response<HashMap<String, String>>) {
                if(response.isSuccessful){
                    if(response.body() != null) {
                        val map: HashMap<String, String> = response.body()!!
                        totalBillGeneratedBinding.displayTotalStudentEnrolled.text = map["noOfStudents"]
                        totalBillGeneratedBinding.textViewCostPerStudentInt.text = map["costPerStudent"]
                        totalBillGeneratedBinding.textViewTotalDue.text = map["totalDue"]
                    }
                    else
                        Toast.makeText(context, "No students found", Toast.LENGTH_SHORT).show()
                }
                else
                    Toast.makeText(context, "Sever Error", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<HashMap<String, String>>, t: Throwable) {
                Log.d("failure", t.localizedMessage)
                Toast.makeText(context, t.localizedMessage, Toast.LENGTH_SHORT).show()
            }

        })

        return totalBillGeneratedBinding.root
    }



}