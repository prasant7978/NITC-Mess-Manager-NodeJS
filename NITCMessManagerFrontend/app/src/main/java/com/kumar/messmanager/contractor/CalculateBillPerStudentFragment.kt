package com.kumar.messmanager.contractor

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import com.google.android.material.snackbar.Snackbar
import com.kumar.messmanager.R
import com.kumar.messmanager.databinding.FragmentCalculateBillPerStudentBinding
import com.kumar.messmanager.model.Student
import com.kumar.messmanager.services.BillServices
import com.kumar.messmanager.services.ProfileService
import com.kumar.messmanager.services.ServiceBuilder
import com.kumar.messmanager.viewmodels.SharedViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CalculateBillPerStudentFragment(var thisFragmentManager:FragmentManager) : Fragment() {

    lateinit var calculateBillPerStudentBinding: FragmentCalculateBillPerStudentBinding
    val sharedViewModel: SharedViewModel by activityViewModels()
    private var totalEnrolledStudent = 0
    private var messName = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        calculateBillPerStudentBinding = FragmentCalculateBillPerStudentBinding.inflate(inflater,container,false)

        retrieveCostPerDayFromDb()

        calculateBillPerStudentBinding.buttonGenerateBill.setOnClickListener {
            val days = calculateBillPerStudentBinding.textInputLayoutNoOfDaysInt.text.toString()

            if(days.toIntOrNull() == null){
                Toast.makeText(activity,"Please, enter valid no. of days",Toast.LENGTH_SHORT).show()
            }
            else if(days.toInt() <= 0){
                Toast.makeText(activity,"No. of days can't be zero or negative",Toast.LENGTH_SHORT).show()
            }
            else if(calculateBillPerStudentBinding.textInputLayoutNoOfDaysInt.text.isEmpty()){
                Toast.makeText(activity,"Please, enter number of days to calculate",Toast.LENGTH_SHORT).show()
            }
            else{
                showAlertMessage()
            }
        }

        return calculateBillPerStudentBinding.root
    }

    private fun showAlertMessage() {
        val dialog = activity?.let { AlertDialog.Builder(it) }
        dialog?.setCancelable(false)
        dialog?.setTitle("Generate Bill")
        dialog?.setMessage("Confirm generate bill")
        dialog?.setNegativeButton("No", DialogInterface.OnClickListener{ dialog, which ->
            dialog.cancel()
        })
        dialog?.setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
            generateBill()
        })
        dialog?.create()?.show()
    }

    private fun generateBill() {
        val days = calculateBillPerStudentBinding.textInputLayoutNoOfDaysInt.text.toString().toInt()

        val billServices: BillServices = ServiceBuilder.buildService(BillServices::class.java)
        val requestCall = billServices.generateBill(sharedViewModel.token, days)

        requestCall.enqueue(object: Callback<Boolean>{
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if(response.isSuccessful){
                    Snackbar.make(calculateBillPerStudentBinding.constraintLayoutCalculateBillPerStudent,"Bill Generated successfully",
                        Snackbar.LENGTH_LONG).setAction("Close", View.OnClickListener { }).show()

                    val fragmentManager : FragmentManager = thisFragmentManager
                    val fragmentTransaction : FragmentTransaction = fragmentManager.beginTransaction()
                    val totalBillGeneratedFragment = TotalBillGeneratedFragment()

                    fragmentTransaction.replace(R.id.frameLayoutBill,totalBillGeneratedFragment)
                    fragmentTransaction.commit()
                }
                else
                    Toast.makeText(context, "Server Error", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Log.d("failure", t.localizedMessage)
                Toast.makeText(context, t.localizedMessage, Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun retrieveCostPerDayFromDb() {
        calculateBillPerStudentBinding.displayCostPerDayInt.text = sharedViewModel.contractor.costPerDay.toString()
        totalEnrolledStudent = sharedViewModel.contractor.totalEnrolled
        messName = sharedViewModel.contractor.messName
    }

}