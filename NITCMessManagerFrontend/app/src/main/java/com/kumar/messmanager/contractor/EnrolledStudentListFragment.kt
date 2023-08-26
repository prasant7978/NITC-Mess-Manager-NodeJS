package com.kumar.messmanager.contractor

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
import com.kumar.messmanager.databinding.FragmentEnrolledStudentListBinding
import com.kumar.messmanager.model.Student
import com.kumar.messmanager.services.ProfileService
import com.kumar.messmanager.services.ServiceBuilder
import com.kumar.messmanager.viewmodels.SharedViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EnrolledStudentListFragment : Fragment() {

    private lateinit var enrolledStudentListBinding: FragmentEnrolledStudentListBinding
    private var studentList = ArrayList<Student>()
    private lateinit var studentAdapter: EnrolledStudentAdapter
    val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        enrolledStudentListBinding = FragmentEnrolledStudentListBinding.inflate(inflater,container,false)

        retrieveEnrolledStudentListFromDb()

        enrolledStudentListBinding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                enrolledStudentListBinding.search.clearFocus()
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                searchList(newText)
                return true
            }
        })

        return enrolledStudentListBinding.root
    }

    private fun searchList(newText: String) {
        val searchList = ArrayList<Student>()
        for(student in studentList){
            if(student.studentRollNo.lowercase().contains(newText.lowercase())){
                searchList.add(student)
            }
        }
        studentAdapter.searchByRoll(searchList)
    }

    private fun retrieveEnrolledStudentListFromDb() {
        enrolledStudentListBinding.progressBar.visibility = View.VISIBLE

        val profileService: ProfileService = ServiceBuilder.buildService(ProfileService::class.java)
        val requestCall = profileService.getAllEnrolledStudent(sharedViewModel.token, sharedViewModel.contractor.messName)

        requestCall.enqueue(object: Callback<ArrayList<Student>>{
            override fun onResponse(call: Call<ArrayList<Student>>, response: Response<ArrayList<Student>>) {
                if(response.isSuccessful){
                    if(response.body() != null){
                        studentList = response.body()!!

                        if(studentList.isEmpty()){
                            enrolledStudentListBinding.search.visibility = View.INVISIBLE
                            enrolledStudentListBinding.recyclerView.visibility = View.INVISIBLE
                            enrolledStudentListBinding.textViewNoStudentEnrolled.visibility = View.VISIBLE
                        }
                        else{
                            studentList.reverse()
                            enrolledStudentListBinding.recyclerView.layoutManager = LinearLayoutManager(activity)
                            studentAdapter = EnrolledStudentAdapter(studentList)
                            enrolledStudentListBinding.recyclerView.adapter = studentAdapter
                        }
                    }
                    else {
                        enrolledStudentListBinding.search.visibility = View.INVISIBLE
                        enrolledStudentListBinding.recyclerView.visibility = View.INVISIBLE
                        enrolledStudentListBinding.textViewNoStudentEnrolled.visibility = View.VISIBLE
                    }
                }
                else
                    Toast.makeText(context, "Server Error", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<ArrayList<Student>>, t: Throwable) {
                Log.d("failure", t.localizedMessage)
                Toast.makeText(context, t.localizedMessage, Toast.LENGTH_SHORT).show()
            }

        })

        enrolledStudentListBinding.progressBar.visibility = View.INVISIBLE

//        val uid = FirebaseAuth.getInstance().currentUser?.uid
//        ref.orderByChild("contractorId").equalTo(uid).addListenerForSingleValueEvent(object :
//            ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                studentList.clear()
//                for(fd in snapshot.children){
//                    val contractor = fd.getValue(Contractor::class.java)
//                    val student = contractor?.studentEnrolled
//                    if(student?.isNotEmpty() == true)
//                        studentList = student
//                }
//
//                if(studentList.isEmpty()){
//                    enrolledStudentListBinding.search.visibility = View.INVISIBLE
//                    enrolledStudentListBinding.recyclerView.visibility = View.INVISIBLE
//                    enrolledStudentListBinding.textViewNoStudentEnrolled.visibility = View.VISIBLE
//                }
//                else {
//                    studentList.reverse()
//                    enrolledStudentListBinding.recyclerView.layoutManager =
//                        LinearLayoutManager(activity)
//                    studentAdapter = EnrolledStudentAdapter(studentList)
//                    enrolledStudentListBinding.recyclerView.adapter = studentAdapter
//                }
//                enrolledStudentListBinding.progressBar.visibility = View.INVISIBLE
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
//            }
//
//        })
    }

}