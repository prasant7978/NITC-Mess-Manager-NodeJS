package com.kumar.messmanager.admin

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.kumar.messmanager.R
import com.kumar.messmanager.authentication.SignUpActivity
import com.kumar.messmanager.databinding.FragmentManageStudentBinding
import com.kumar.messmanager.model.Student

class ManageStudentFragment : Fragment() {

    lateinit var manageStudentBinding: FragmentManageStudentBinding
    val studentList = ArrayList<Student>()
//    val db : FirebaseDatabase = FirebaseDatabase.getInstance()
//    val ref = db.reference.child("students")
    lateinit var studentAdapter: StudentAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        manageStudentBinding = FragmentManageStudentBinding.inflate(inflater, container, false)

//        retrieveStudentListFromDb()

        manageStudentBinding.textViewAddStudent.setOnClickListener {
            val intent = Intent(context, SignUpActivity::class.java)
            startActivity(intent)

//            val fragmentManager : FragmentManager = requireActivity().supportFragmentManager
//            val fragmentTransaction : FragmentTransaction = fragmentManager.beginTransaction()
//            val addStudentFragment = AddStudentFragment()
//
//            fragmentTransaction.replace(R.id.frameLayout,addStudentFragment)
//            fragmentTransaction.addToBackStack(null)
//            fragmentTransaction.commit()
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

//    private fun retrieveStudentListFromDb(){
//        manageStudentBinding.progressBar.visibility = View.VISIBLE
//        ref.addValueEventListener(object : ValueEventListener{
//            override fun onDataChange(snapshot: DataSnapshot) {
//                studentList.clear()
//                for(std in snapshot.children){
//                    val student = std.getValue(Student::class.java)
//                    if(student != null){
//                        studentList.add(student)
//                    }
//                }
//
//                if(studentList.isEmpty()){
//                    manageStudentBinding.textViewNoStudentEnrolled.visibility = View.VISIBLE
//                    manageStudentBinding.recyclerViewStudentList.visibility = View.INVISIBLE
//                    manageStudentBinding.search.visibility = View.INVISIBLE
//                }
//                else {
//                    studentList.reverse()
//                    manageStudentBinding.recyclerViewStudentList.layoutManager =
//                        LinearLayoutManager(activity)
//                    studentAdapter = StudentAdapter(studentList)
//                    manageStudentBinding.recyclerViewStudentList.adapter = studentAdapter
//                }
//
//                manageStudentBinding.progressBar.visibility = View.INVISIBLE
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
//            }
//
//        })
//    }
}