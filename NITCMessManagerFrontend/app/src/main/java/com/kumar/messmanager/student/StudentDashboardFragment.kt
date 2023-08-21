package com.kumar.messmanager.student

import android.content.Context
import android.os.Bundle
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
import com.kumar.messmanager.contractor.ManageMessMenuFragment
import com.kumar.messmanager.databinding.FragmentStudentDashboardBinding
import com.kumar.messmanager.model.Student
import com.kumar.messmanager.services.ProfileService
import com.kumar.messmanager.services.ServiceBuilder
import com.kumar.messmanager.student.access.GetProfileAccess
import com.kumar.messmanager.viewmodels.SharedViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StudentDashboardFragment : Fragment() {

    private lateinit var studentDashboardFragmentBinding: FragmentStudentDashboardBinding
    private val sharedViewModel: SharedViewModel by activityViewModels()
    var messName : String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        studentDashboardFragmentBinding = FragmentStudentDashboardBinding.inflate(inflater,container,false)

        val getProfileAccess = GetProfileAccess(this)
        val getStudentCoroutineScope = CoroutineScope(Dispatchers.Main)

        getStudentCoroutineScope.launch {
            val student = getProfileAccess.retrieveStudentInfo(sharedViewModel)
            if(student != null) {
                studentDashboardFragmentBinding.textViewName.text = student.studentName
            }

            getStudentCoroutineScope.cancel()
        }

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
                val fragmentManager : FragmentManager = requireActivity().supportFragmentManager
                val fragmentTransaction : FragmentTransaction = fragmentManager.beginTransaction()
                val studentFeedbackFragment = StudentFeedbackFragment()

                fragmentTransaction.replace(R.id.frameLayout,studentFeedbackFragment)
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
            }
        }

        return studentDashboardFragmentBinding.root
    }

}