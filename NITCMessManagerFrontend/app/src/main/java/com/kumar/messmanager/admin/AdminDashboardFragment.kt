package com.kumar.messmanager.admin

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import com.kumar.messmanager.R
import com.kumar.messmanager.databinding.FragmentAdminDashboardBinding
import com.kumar.messmanager.model.Admin
import com.kumar.messmanager.services.ProfileService
import com.kumar.messmanager.services.ServiceBuilder
import com.kumar.messmanager.viewmodels.SharedViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminDashboardFragment : Fragment() {

    private lateinit var adminDashboardBinding: FragmentAdminDashboardBinding
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        adminDashboardBinding = FragmentAdminDashboardBinding.inflate(inflater, container, false)

        retrieveAdminInfo()

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

    private fun retrieveAdminInfo(){
        val sharedPreferences = this.activity?.getSharedPreferences("saveToken", Context.MODE_PRIVATE)
        val token = sharedPreferences?.getString("token", "")

        val profileService: ProfileService = ServiceBuilder.buildService(ProfileService::class.java)
        val requestCall = profileService.getAdminProfileWithToken(token.toString())

        requestCall.enqueue(object: Callback<Admin>{
            override fun onResponse(call: Call<Admin>, response: Response<Admin>) {
                if(response.isSuccessful){
                    if(response.body() != null){
                        val admin = response.body()
                        adminDashboardBinding.textViewAdminName.text = admin!!.adminEmail

                        sharedViewModel.admin = admin
                        sharedViewModel.token = token!!
                        sharedViewModel.userType = admin.userType
                    }
                    else
                        Toast.makeText(context, "Admin not found...", Toast.LENGTH_SHORT).show()
                }
                else
                    Toast.makeText(context, "Some error occurred...", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<Admin>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

}