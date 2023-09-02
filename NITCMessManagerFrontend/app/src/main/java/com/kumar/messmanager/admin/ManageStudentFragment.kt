package com.kumar.messmanager.admin

import android.content.Intent
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
import com.kumar.messmanager.authentication.SignUpActivity
import com.kumar.messmanager.databinding.FragmentManageStudentBinding
import com.kumar.messmanager.model.Student
import com.kumar.messmanager.services.ProfileService
import com.kumar.messmanager.services.ServiceBuilder
import com.kumar.messmanager.viewmodels.SharedViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ManageStudentFragment : Fragment() {

    private lateinit var manageStudentBinding: FragmentManageStudentBinding
    private var studentList = ArrayList<Student>()
    private lateinit var studentAdapter: StudentAdapter
    val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        manageStudentBinding = FragmentManageStudentBinding.inflate(inflater, container, false)

        retrieveStudentListFromDb()

        manageStudentBinding.textViewAddStudent.setOnClickListener {
            val fragmentManager : FragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction : FragmentTransaction = fragmentManager.beginTransaction()
            val addStudentFragment = AddStudentFragment()

            fragmentTransaction.replace(R.id.frameLayout,addStudentFragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        manageStudentBinding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    manageStudentBinding.search.clearFocus()
                    return false
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    searchList(newText)
                    return true
                }
            }
        )

        return manageStudentBinding.root
    }

    fun searchList(text : String){
        val searchList = ArrayList<Student>()
        for(student in studentList){
            if(student.studentRollNo.lowercase().contains(text.lowercase())){
                searchList.add(student)
            }
        }
        studentAdapter.searchByRoll(searchList)
    }

    private fun retrieveStudentListFromDb(){
        manageStudentBinding.progressBar.visibility = View.VISIBLE

        val profileService: ProfileService = ServiceBuilder.buildService(ProfileService::class.java)
        val requestCall = profileService.getAllStudents(sharedViewModel.token)

        requestCall.enqueue(object: Callback<ArrayList<Student>>{
            override fun onResponse(call: Call<ArrayList<Student>>, response: Response<ArrayList<Student>>) {
                if(response.isSuccessful){
                    if(response.body() != null) {
                        studentList = response.body()!!

                        if (studentList.isEmpty()) {
                            manageStudentBinding.textViewNoStudentEnrolled.visibility = View.VISIBLE
                            manageStudentBinding.recyclerViewStudentList.visibility = View.INVISIBLE
                            manageStudentBinding.search.visibility = View.INVISIBLE
                        }
                        else {
                            studentList.reverse()
                            manageStudentBinding.recyclerViewStudentList.layoutManager = LinearLayoutManager(activity)
                            studentAdapter = StudentAdapter(studentList)
                            manageStudentBinding.recyclerViewStudentList.adapter = studentAdapter
                        }
                    }
                    else{
                        manageStudentBinding.textViewNoStudentEnrolled.visibility = View.VISIBLE
                        manageStudentBinding.recyclerViewStudentList.visibility = View.INVISIBLE
                        manageStudentBinding.search.visibility = View.INVISIBLE
                    }
                }
                else
                    Toast.makeText(context, "Server Error", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<ArrayList<Student>>, t: Throwable) {
                Log.d("viewStudents", t.localizedMessage)
                Toast.makeText(context, t.localizedMessage, Toast.LENGTH_SHORT).show()
            }

        })

        manageStudentBinding.progressBar.visibility = View.INVISIBLE
    }
}