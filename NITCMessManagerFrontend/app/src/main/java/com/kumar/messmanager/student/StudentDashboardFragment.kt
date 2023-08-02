package com.kumar.messmanager.student

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.snackbar.Snackbar
import com.kumar.messmanager.R
import com.kumar.messmanager.contractor.ManageMessMenuFragment
import com.kumar.messmanager.databinding.FragmentStudentDashboardBinding

class StudentDashboardFragment : Fragment() {

    lateinit var studentDashboardFragmentBinding: FragmentStudentDashboardBinding
//    var db : FirebaseDatabase = FirebaseDatabase.getInstance()
//    var ref = db.reference.child("students")
    var messName : String = ""
    var studentName : String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        studentDashboardFragmentBinding = FragmentStudentDashboardBinding.inflate(inflater,container,false)

//        val uid = FirebaseAuth.getInstance().currentUser?.uid

        var userType = ""

//        ref.orderByChild("studentId").equalTo(uid).addListenerForSingleValueEvent(object :
//            ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                for(ds in snapshot.children) {
//                    Log.d("debug",ds.child("studentName").value.toString())
//                    studentDashboardFragmentBinding.textViewName.text = ds.child("studentName").value.toString()
//                    studentName = ds.child("studentName").value.toString()
//                    messName = ds.child("messEnrolled").value.toString()
//                    userType = ds.child("userType").value.toString()
//                }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
//            }
//
//        })

        studentDashboardFragmentBinding.studentProfile.setOnClickListener {
            val fragmentManager : FragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction : FragmentTransaction = fragmentManager.beginTransaction()
            val studentProfileFragment = StudentProfileFragment()

            fragmentTransaction.replace(R.id.frameLayout,studentProfileFragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        studentDashboardFragmentBinding.studentSelectMess.setOnClickListener {
            val fragmentManager : FragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction : FragmentTransaction = fragmentManager.beginTransaction()
            val showMessListFragment = ShowMessListFragment()

            val bundle = Bundle()
            bundle.putString("userType",userType)

            showMessListFragment.arguments = bundle

            fragmentTransaction.replace(R.id.frameLayout,showMessListFragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        studentDashboardFragmentBinding.showMessMenu.setOnClickListener {
            if(messName.isEmpty()){
                Snackbar.make(studentDashboardFragmentBinding.linearLayoutStudentDashboard,"Please enroll in a mess to see the menu.",
                    Snackbar.LENGTH_LONG).setAction("Close", View.OnClickListener { }).show()
            }
            else {
                val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
                val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
                val manageMessMenuFragment = ManageMessMenuFragment()

                val bundle = Bundle()
                bundle.putString("userType", userType)
                bundle.putString("messName", messName)

                manageMessMenuFragment.arguments = bundle

                fragmentTransaction.replace(R.id.frameLayout, manageMessMenuFragment)
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
            }
        }

        studentDashboardFragmentBinding.constraintLayoutPayment.setOnClickListener {
            val fragmentManager : FragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction : FragmentTransaction = fragmentManager.beginTransaction()
            val studentPaymentFragment = StudentPaymentFragment()

            fragmentTransaction.replace(R.id.frameLayout,studentPaymentFragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        studentDashboardFragmentBinding.buttonFeedback.setOnClickListener {
            if(messName.isEmpty()){
                Snackbar.make(studentDashboardFragmentBinding.linearLayoutStudentDashboard,"You have not enrolled in any mess yet!",
                    Snackbar.LENGTH_LONG).setAction("Close", View.OnClickListener { }).show()
            }
            else {
                val bundle = Bundle()
                bundle.putString("studentName",studentName)
                bundle.putString("messName",messName)

                val fragmentManager : FragmentManager = requireActivity().supportFragmentManager
                val fragmentTransaction : FragmentTransaction = fragmentManager.beginTransaction()
                val studentFeedbackFragment = StudentFeedbackFragment()

                studentFeedbackFragment.arguments = bundle

                fragmentTransaction.replace(R.id.frameLayout,studentFeedbackFragment)
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
            }
        }

        return studentDashboardFragmentBinding.root
    }

}