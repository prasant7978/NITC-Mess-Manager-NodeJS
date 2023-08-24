package com.kumar.messmanager.student

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.kumar.messmanager.databinding.FragmentShowMessListBinding
import com.kumar.messmanager.model.Contractor
import com.kumar.messmanager.services.ProfileService
import com.kumar.messmanager.services.ServiceBuilder
import com.kumar.messmanager.viewmodels.SharedViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ShowMessListFragment : Fragment() {

    private lateinit var showMessListBinding: FragmentShowMessListBinding
    private var messList = ArrayList<Contractor>()
    private lateinit var messAdapter: MessAdapter
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        showMessListBinding = FragmentShowMessListBinding.inflate(inflater,container,false)

        retrieveMessDetailsFromDb()

        showMessListBinding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                showMessListBinding.search.clearFocus()
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                searchList(newText)
                return true
            }
        }
        )

        return showMessListBinding.root
    }

    fun searchList(text : String){
        val searchList = ArrayList<Contractor>()
        for(contractor in messList){
            if(contractor.foodType.lowercase().contains(text.lowercase())){
                searchList.add(contractor)
            }
        }
        messAdapter.searchByFoodType(searchList)
    }

    private fun retrieveMessDetailsFromDb() {
        showMessListBinding.progressBar.visibility = View.VISIBLE

        val token = sharedViewModel.token
        val profileService: ProfileService = ServiceBuilder.buildService(ProfileService::class.java)
        val requestCall = profileService.getAllContractorProfile(token)

        requestCall.enqueue(object: Callback<ArrayList<Contractor>>{
            override fun onResponse(call: Call<ArrayList<Contractor>>, response: Response<ArrayList<Contractor>>) {
                if(response.isSuccessful){
                    Log.d("agaya", "aagaya")
                    if(response.body() != null){
                        messList = response.body()!!
                        messList.reverse()
                        showMessListBinding.recyclerViewMessList.layoutManager = LinearLayoutManager(activity)
                        messAdapter = MessAdapter(messList)
                        showMessListBinding.recyclerViewMessList.adapter = messAdapter
                    }
                    else{
                        showMessListBinding.textViewAvailableMess.visibility = View.INVISIBLE
                        showMessListBinding.textViewNoContractorRegistered.visibility = View.VISIBLE
                        showMessListBinding.search.visibility = View.INVISIBLE
                    }
                }
                else
                    Toast.makeText(context, "Server Error", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<ArrayList<Contractor>>, t: Throwable) {
                Log.d("aagaya", t.localizedMessage.toString())
                Toast.makeText(context, t.localizedMessage, Toast.LENGTH_SHORT).show()
            }

        })

        showMessListBinding.progressBar.visibility = View.INVISIBLE
    }

}