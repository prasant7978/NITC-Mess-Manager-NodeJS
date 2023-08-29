package com.kumar.messmanager.student

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import com.google.android.material.snackbar.Snackbar
import com.kumar.messmanager.R
import com.kumar.messmanager.databinding.FragmentStudentPaymentBinding
import com.kumar.messmanager.model.Contractor
import com.kumar.messmanager.model.Student
import com.kumar.messmanager.services.BillServices
import com.kumar.messmanager.services.ServiceBuilder
import com.kumar.messmanager.viewmodels.SharedViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StudentPaymentFragment : Fragment() {

    private lateinit var studentPaymentBinding: FragmentStudentPaymentBinding
    val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        studentPaymentBinding = FragmentStudentPaymentBinding.inflate(inflater,container,false)

        studentPaymentBinding.textViewDues.text = sharedViewModel.student.messBill.toString()

        studentPaymentBinding.buttonPayBill.setOnClickListener {
            payBill()
        }

        return studentPaymentBinding.root
    }

    private fun payBill(){
        studentPaymentBinding.buttonPayBill.isClickable = false
        
        if(sharedViewModel.student.messBill != 0){
            val dialog = AlertDialog.Builder(activity)
            dialog.setTitle("Pay Mess Bill")
            dialog.setCancelable(false)
            dialog.setMessage("A amount of " + sharedViewModel.student.messBill + " will be credited from your bank account")
            dialog.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which ->
                dialog.cancel()
            })
            dialog.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                updateDb()
            })
            dialog.create().show()
        }
        else{
            val dialog = AlertDialog.Builder(activity)
            dialog.setTitle("Pay Mess Bill")
            dialog.setCancelable(false)
            dialog.setMessage("You don't have any due to pay")
            dialog.setNegativeButton("OK", DialogInterface.OnClickListener { dialog, which ->
                dialog.cancel()
            })
            dialog.create().show()
        }
        studentPaymentBinding.buttonPayBill.isClickable = true
    }

    private fun updateDb() {
        val billServices: BillServices = ServiceBuilder.buildService(BillServices::class.java)
        val requestCall = billServices.payBill(sharedViewModel.token, sharedViewModel.student.messBill, sharedViewModel.student.messEnrolled)
        Log.d("mess", sharedViewModel.student.messEnrolled)

        requestCall.enqueue(object: Callback<Boolean>{
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if(response.isSuccessful){
                    Snackbar.make(studentPaymentBinding.constraintPaymentLayout,"Your payment was successful", Snackbar.LENGTH_LONG).setAction("Close", View.OnClickListener { }).show()

                    val fragmentManager : FragmentManager = requireActivity().supportFragmentManager
                    val fragmentTransaction : FragmentTransaction = fragmentManager.beginTransaction()
                    val studentDashboardFragment = StudentDashboardFragment()

                    fragmentTransaction.replace(R.id.frameLayout,studentDashboardFragment)
                    fragmentTransaction.addToBackStack(null)
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

//        ref.orderByChild("studentId").equalTo(uid).addListenerForSingleValueEvent(object : ValueEventListener{
//            override fun onDataChange(snapshot: DataSnapshot) {
//                for(std in snapshot.children){
//                    val student = std.getValue(Student::class.java)
//                    if(student != null){
//                        bill = student.messBill
//                        messName = student.messEnrolled
//
//                        student.messBill = 0
//                        student.messEnrolled = ""
//                        student.paymentStatus = "paid"
//
//                        ref.child(student.studentId).setValue(student)
//                    }
//                }
//
//                Log.d("payment",bill.toString())
//                Log.d("payment",messName)
//
//                var contractorUid = ""
//                ref_contractor.orderByChild("messName").equalTo(messName).addListenerForSingleValueEvent(object : ValueEventListener{
//                    override fun onDataChange(snapshot: DataSnapshot) {
//                        for (ds in snapshot.children){
//                            contractorUid = ds.child("contractorId").value.toString()
//                            Log.d("payment",contractorUid)
//                        }
//
//                        Log.d("payment",contractorUid)
//
//                        ref_contractor.orderByChild("contractorId").equalTo(contractorUid)
//                            .addListenerForSingleValueEvent(object : ValueEventListener {
//                                override fun onDataChange(snapshot: DataSnapshot) {
//                                    for(ds in snapshot.children){
//                                        val contractor : Contractor = ds.getValue(Contractor::class.java)!!
//
//                                        Log.d("payment",contractor.availability.toString())
//                                        contractor.availability = contractor.availability + 1
//                                        Log.d("payment",contractor.availability.toString())
//                                        Log.d("payment",contractor.totalDue.toString())
//                                        contractor.totalDue = contractor.totalDue - bill
//                                        Log.d("payment",contractor.totalDue.toString())
//                                        for(std in contractor.studentEnrolled){
//                                            if(std.studentId == uid){
//                                                contractor.studentEnrolled.remove(std)
//                                                break
//                                            }
//                                        }
//
//                                        ref_contractor.child(contractor.contractorId).setValue(contractor)
//                                    }
//                                }
//
//                                override fun onCancelled(error: DatabaseError) {
//                                    TODO("Not yet implemented")
//                                }
//
//                            })
    //                        retrieveDueAmount(uid)
//
//                    }
//
//                    override fun onCancelled(error: DatabaseError) {
//                        TODO("Not yet implemented")
//                    }
//
//                })
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
//            }
//
//        })
    }

}