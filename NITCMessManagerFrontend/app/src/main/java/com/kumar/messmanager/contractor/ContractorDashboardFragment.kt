package com.kumar.messmanager.contractor

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
import com.kumar.messmanager.databinding.FragmentContractorDashboardBinding
import com.kumar.messmanager.model.Contractor
import com.kumar.messmanager.services.GetProfileService
import com.kumar.messmanager.services.ServiceBuilder
import com.kumar.messmanager.viewmodels.SharedViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ContractorDashboardFragment : Fragment() {

    private lateinit var contractorDashboardBinding: FragmentContractorDashboardBinding
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private var noOfEnrolledStudent = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        contractorDashboardBinding = FragmentContractorDashboardBinding.inflate(inflater,container,false)

        retrieveContractorInfo()

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
            if(noOfEnrolledStudent != 0) {
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

    private fun retrieveContractorInfo(){
        val sharedPreferences = this.activity?.getSharedPreferences("saveToken", Context.MODE_PRIVATE)
        val token = sharedPreferences?.getString("token", "")

        val profileService: GetProfileService = ServiceBuilder.buildService(GetProfileService::class.java)
        val requestCall = profileService.getContractorProfileWithToken(token.toString())

        requestCall.enqueue(object: Callback<Contractor>{
            override fun onResponse(call: Call<Contractor>, response: Response<Contractor>) {
                if(response.isSuccessful){
                    if(response.body() != null){
                        val contractor = response.body()
                        noOfEnrolledStudent = contractor!!.studentEnrolled.size
                        contractorDashboardBinding.textViewContractorName.text = contractor.contractorName

                        sharedViewModel.contractor = contractor
                        sharedViewModel.userType = contractor.userType
                        sharedViewModel.token = token.toString()
                    }
                    else
                        Toast.makeText(context, "Contractor not found...", Toast.LENGTH_SHORT).show()
                }
                else
                    Toast.makeText(context, "Some error occurred...", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<Contractor>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}