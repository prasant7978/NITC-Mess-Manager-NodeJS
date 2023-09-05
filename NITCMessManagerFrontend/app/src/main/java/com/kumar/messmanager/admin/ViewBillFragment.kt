package com.kumar.messmanager.admin

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
import com.kumar.messmanager.databinding.FragmentViewBillBinding
import com.kumar.messmanager.model.Student
import com.kumar.messmanager.services.ProfileService
import com.kumar.messmanager.services.ServiceBuilder
import com.kumar.messmanager.viewmodels.SharedViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewBillFragment : Fragment() {

    lateinit var mangeBillBinding: FragmentViewBillBinding
    private var studentList = ArrayList<Student>()
    private lateinit var studentBillAdapter: StudentBillAdapter
    val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mangeBillBinding = FragmentViewBillBinding.inflate(inflater,container,false)

        retrievePaymentDetailsFromDb()

        mangeBillBinding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                mangeBillBinding.search.clearFocus()
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                searchList(newText)
                return true
            }
        }
        )

        return mangeBillBinding.root
    }

    private fun searchList(newText: String) {
        val searchList = ArrayList<Student>()
        for(student in studentList){
            if(student.studentRollNo.lowercase().contains(newText.lowercase())){
                searchList.add(student)
            }
        }
        studentBillAdapter.searchByRoll(searchList)
    }

    private fun retrievePaymentDetailsFromDb() {
        mangeBillBinding.progressBar.visibility = View.VISIBLE

        val profileService: ProfileService = ServiceBuilder.buildService(ProfileService::class.java)
        val requestCall = profileService.getAllStudents(sharedViewModel.token)

        requestCall.enqueue(object: Callback<ArrayList<Student>>{
            override fun onResponse(call: Call<ArrayList<Student>>, response: Response<ArrayList<Student>>) {
                if(response.isSuccessful){
                    if(response.body() != null) {
                        studentList = response.body()!!
                        if (studentList.isEmpty()) {
                            mangeBillBinding.textViewNoContractorRegistered.visibility = View.VISIBLE
                            mangeBillBinding.search.visibility = View.INVISIBLE
                            mangeBillBinding.recyclerViewMessDueList.visibility = View.INVISIBLE
                        }
                        else {
                            studentList.reverse()
                            mangeBillBinding.recyclerViewMessDueList.layoutManager = LinearLayoutManager(activity)
                            studentBillAdapter = StudentBillAdapter(studentList)
                            mangeBillBinding.recyclerViewMessDueList.adapter = studentBillAdapter
                        }
                    }
                    else{
                        mangeBillBinding.textViewNoContractorRegistered.visibility = View.VISIBLE
                        mangeBillBinding.search.visibility = View.INVISIBLE
                        mangeBillBinding.recyclerViewMessDueList.visibility = View.INVISIBLE
                    }
                }
                else
                    Toast.makeText(context, "Server Error", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<ArrayList<Student>>, t: Throwable) {
                Log.d("viewBills", t.localizedMessage)
                Toast.makeText(context, t.localizedMessage, Toast.LENGTH_SHORT).show()
            }

        })

        mangeBillBinding.progressBar.visibility = View.INVISIBLE
    }

}