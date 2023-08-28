package com.kumar.messmanager.contractor

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import com.google.android.material.snackbar.Snackbar
import com.kumar.messmanager.R
import com.kumar.messmanager.contractor.access.GetProfileAccess
import com.kumar.messmanager.databinding.FragmentContractorDashboardBinding
import com.kumar.messmanager.viewmodels.SharedViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class ContractorDashboardFragment : Fragment() {

    private lateinit var contractorDashboardBinding: FragmentContractorDashboardBinding
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private var noOfEnrolledStudent = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        contractorDashboardBinding = FragmentContractorDashboardBinding.inflate(inflater,container,false)

        val getProfileAccess = GetProfileAccess(this)
        val getContractorCoroutineScope = CoroutineScope(Dispatchers.Main)

        getContractorCoroutineScope.launch {
            val contractor = getProfileAccess.retrieveContractorInfo(sharedViewModel)
            if(contractor != null) {
                noOfEnrolledStudent = contractor.totalEnrolled
                Log.d("enroll", noOfEnrolledStudent.toString())
                contractorDashboardBinding.textViewContractorName.text = contractor.contractorName
            }

            getContractorCoroutineScope.cancel()
        }


        contractorDashboardBinding.profile.setOnClickListener {
            val fragmentManager : FragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction : FragmentTransaction = fragmentManager.beginTransaction()
            val contractorProfile = ContractorProfileFragment()

            fragmentTransaction.replace(R.id.frameLayout,contractorProfile)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        contractorDashboardBinding.messMenu.setOnClickListener {
            val fragmentManager : FragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction : FragmentTransaction = fragmentManager.beginTransaction()
            val manageMessMenuFragment = ManageMessMenuFragment()

            fragmentTransaction.replace(R.id.frameLayout,manageMessMenuFragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        contractorDashboardBinding.generateBill.setOnClickListener {
            if(noOfEnrolledStudent > 0) {
                val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
                val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
                val generateBillFragment = GenerateBillFragment()

                fragmentTransaction.replace(R.id.frameLayout, generateBillFragment)
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
            }else{
                Snackbar.make(contractorDashboardBinding.contractorDashboardLayout,"No student has been enrolled yet.",
                    Snackbar.LENGTH_LONG).setAction("Close", View.OnClickListener { }).show()
            }
        }

        contractorDashboardBinding.checkFeedback.setOnClickListener {
            val fragmentManager : FragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction : FragmentTransaction = fragmentManager.beginTransaction()
            val feedbackFragment = FeedbackFragment()

            fragmentTransaction.replace(R.id.frameLayout,feedbackFragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        contractorDashboardBinding.enrolledStudent.setOnClickListener {
            val fragmentManager : FragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction : FragmentTransaction = fragmentManager.beginTransaction()
            val studentEnrolled = EnrolledStudentListFragment()

            fragmentTransaction.replace(R.id.frameLayout,studentEnrolled)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        return contractorDashboardBinding.root
    }
}