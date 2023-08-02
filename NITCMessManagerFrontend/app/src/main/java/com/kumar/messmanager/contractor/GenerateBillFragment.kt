package com.kumar.messmanager.contractor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.kumar.messmanager.R
import com.kumar.messmanager.databinding.FragmentGenerateBillBinding
import com.kumar.messmanager.model.Contractor

class GenerateBillFragment : Fragment() {

    private lateinit var generateBillBinding: FragmentGenerateBillBinding
//    val db : FirebaseDatabase = FirebaseDatabase.getInstance()
//    val reference = db.reference.child("contractors")

    var totalCostPerStudent = 0
    var totalEnrolledStudent = 0
    var totalDue = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        generateBillBinding = FragmentGenerateBillBinding.inflate(inflater,container,false)

//        val uid = FirebaseAuth.getInstance().currentUser?.uid

//        reference.orderByChild("contractorId").equalTo(uid).addListenerForSingleValueEvent(object : ValueEventListener{
//            override fun onDataChange(snapshot: DataSnapshot) {
//                for(ds in snapshot.children){
//                    val contractor = ds.getValue(Contractor::class.java)
//                    if(contractor != null){
//                        if(contractor.totalDue == 0){
//                            val fragmentManager : FragmentManager = parentFragmentManager
//                            val fragmentTransaction : FragmentTransaction = fragmentManager.beginTransaction()
//                            val calculateBillPerStudentFragment = CalculateBillPerStudentFragment(childFragmentManager)
//
//                            fragmentTransaction.replace(R.id.frameLayoutBill,calculateBillPerStudentFragment)
//                            fragmentTransaction.commit()
//                        }
//                        else{
//                            val fragmentManager : FragmentManager = requireActivity().supportFragmentManager
//                            val fragmentTransaction : FragmentTransaction = fragmentManager.beginTransaction()
//                            val totalBillGeneratedFragment = TotalBillGeneratedFragment()
//
//                            reference.orderByChild("contractorId").equalTo(uid).addListenerForSingleValueEvent(object :
//                                ValueEventListener {
//                                override fun onDataChange(snapshot: DataSnapshot) {
//                                    for(ds in snapshot.children){
//                                        val contractor = ds.getValue(Contractor::class.java)
//                                        if(contractor != null) {
//                                            totalCostPerStudent = contractor.studentEnrolled[0].messBill
//                                            totalEnrolledStudent = contractor.studentEnrolled.size
//                                            totalDue = contractor.totalDue
//                                            break
//                                        }
//                                    }
//
////                                    Log.d("arguments","...inside the function...")
////                                    Log.d("arguments",totalCostPerStudent.toString())
////                                    Log.d("arguments",totalEnrolledStudent.toString())
////                                    Log.d("arguments",totalDue.toString())
//
//                                    val bundle = Bundle()
//                                    bundle.putInt("totalCostPerStudent",totalCostPerStudent)
//                                    bundle.putInt("totalEnrolledStudent",totalEnrolledStudent)
//                                    bundle.putInt("totalDue",totalDue)
//
//                                    totalBillGeneratedFragment.arguments = bundle
//
//                                    fragmentTransaction.replace(R.id.frameLayoutBill,totalBillGeneratedFragment)
//                                    fragmentTransaction.commit()
//                                }
//
//                                override fun onCancelled(error: DatabaseError) {
//                                    TODO("Not yet implemented")
//                                }
//
//                            })
//                        }
//                    }
//                }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
//            }
//
//        })

        return generateBillBinding.root
    }

    private fun retrieveDetailsFromDb() {
//        val uid = FirebaseAuth.getInstance().currentUser?.uid

    }

}