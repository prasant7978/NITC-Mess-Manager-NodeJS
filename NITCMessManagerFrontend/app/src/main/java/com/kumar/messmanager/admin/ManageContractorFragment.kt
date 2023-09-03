package com.kumar.messmanager.admin

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.kumar.messmanager.R
import com.kumar.messmanager.databinding.FragmentManageContractorBinding
import com.kumar.messmanager.model.Contractor
import com.kumar.messmanager.services.ProfileService
import com.kumar.messmanager.services.ServiceBuilder
import com.kumar.messmanager.viewmodels.SharedViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ManageContractorFragment : Fragment() {

    lateinit var manageContractorBinding: FragmentManageContractorBinding
    private var contractorList = ArrayList<Contractor>()
    private lateinit var contractorAdapter: ContractorAdapter
    val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        manageContractorBinding = FragmentManageContractorBinding.inflate(inflater,container,false)

        retrieveContractorListFromDb()

        manageContractorBinding.textViewAddContractor.setOnClickListener {
            val fragmentManager : FragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction : FragmentTransaction = fragmentManager.beginTransaction()
            val addContractorFragment = AddContractorFragment()

            fragmentTransaction.replace(R.id.frameLayout,addContractorFragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        manageContractorBinding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                manageContractorBinding.search.clearFocus()
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                searchList(newText)
                return true
            }
        }
        )

        return manageContractorBinding.root
    }

    fun searchList(text : String){
        val searchList = ArrayList<Contractor>()
        for(contractor in contractorList){
            if(contractor.messName.lowercase().contains(text.lowercase())){
                searchList.add(contractor)
            }
        }
        contractorAdapter.searchByFoodType(searchList)
    }

    private fun retrieveContractorListFromDb() {
        manageContractorBinding.progressBar.visibility = View.VISIBLE

        val profileService: ProfileService = ServiceBuilder.buildService(ProfileService::class.java)
        val requestCall = profileService.getAllContractorProfile(sharedViewModel.token)

        requestCall.enqueue(object: Callback<ArrayList<Contractor>>{
            override fun onResponse(call: Call<ArrayList<Contractor>>, response: Response<ArrayList<Contractor>>) {
                if(response.isSuccessful){
                    if(response.body() != null){
                        contractorList = response.body()!!

                        if(contractorList.isEmpty()){
                            manageContractorBinding.textViewNoContractorRegistered.visibility = View.VISIBLE
                            manageContractorBinding.search.visibility = View.VISIBLE
                            manageContractorBinding.recyclerViewContractorList.visibility = View.VISIBLE
                        }
                        else {
                            contractorList.reverse()
                            manageContractorBinding.recyclerViewContractorList.layoutManager = LinearLayoutManager(activity)
                            contractorAdapter = ContractorAdapter(contractorList, this@ManageContractorFragment)
                            manageContractorBinding.recyclerViewContractorList.adapter = contractorAdapter
                        }
                    }
                    else{
                        manageContractorBinding.textViewNoContractorRegistered.visibility = View.VISIBLE
                        manageContractorBinding.search.visibility = View.VISIBLE
                        manageContractorBinding.recyclerViewContractorList.visibility = View.VISIBLE
                    }
                }
                else
                    Toast.makeText(context, "Server Error", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<ArrayList<Contractor>>, t: Throwable) {
                Log.d("viewContractor", t.localizedMessage)
                Toast.makeText(context, t.localizedMessage, Toast.LENGTH_SHORT).show()
            }

        })

        manageContractorBinding.progressBar.visibility = View.INVISIBLE
    }

}