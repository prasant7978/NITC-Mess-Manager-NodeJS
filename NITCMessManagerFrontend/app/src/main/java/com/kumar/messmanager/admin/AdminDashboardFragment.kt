package com.kumar.messmanager.admin

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.kumar.messmanager.R
import com.kumar.messmanager.databinding.FragmentAdminDashboardBinding

class AdminDashboardFragment : Fragment() {

    lateinit var adminDashboardBinding: FragmentAdminDashboardBinding
//    var db : FirebaseDatabase = FirebaseDatabase.getInstance()
//    var ref = db.reference.child("admin")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        adminDashboardBinding = FragmentAdminDashboardBinding.inflate(inflater, container, false)

//        val uid = FirebaseAuth.getInstance().currentUser?.uid.toString()

//        retrieveAdminInfo(uid)

        adminDashboardBinding.manageStudent.setOnClickListener {
            val fragmentManager : FragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction : FragmentTransaction = fragmentManager.beginTransaction()
            val manageStudent = ManageStudentFragment()

            fragmentTransaction.replace(R.id.frameLayout,manageStudent)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()

        }

        adminDashboardBinding.manageContractor.setOnClickListener {
            val fragmentManager : FragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction : FragmentTransaction = fragmentManager.beginTransaction()
            val manageContractor = ManageContractorFragment()

            fragmentTransaction.replace(R.id.frameLayout,manageContractor)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        adminDashboardBinding.manageBill.setOnClickListener {
            val fragmentManager : FragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction : FragmentTransaction = fragmentManager.beginTransaction()
            val manageBillFragment = ViewBillFragment()

            fragmentTransaction.replace(R.id.frameLayout,manageBillFragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        return adminDashboardBinding.root
    }

//    private fun retrieveAdminInfo(uid : String){
//        ref.orderByChild("adminId").equalTo(uid).addListenerForSingleValueEvent(object  :
//            ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                for(ds in snapshot.children){
//                    adminDashboardBinding.textViewAdminName.setText(ds.child("adminEmail").value.toString())
//                }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
//            }
//
//        })
//    }

}