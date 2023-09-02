package com.kumar.messmanager.admin

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.kumar.messmanager.R
import com.kumar.messmanager.databinding.ItemStudentBinding
import com.kumar.messmanager.model.Student
import com.kumar.messmanager.viewmodels.SharedViewModel

class StudentAdapter(private var studentList : ArrayList<Student>) : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>(){

    class StudentViewHolder(val adapterBinding : ItemStudentBinding) : RecyclerView.ViewHolder(adapterBinding.root){
        val name : TextView = adapterBinding.name
        val roll : TextView = adapterBinding.textViewRollNo
        val email : TextView = adapterBinding.textViewEmail
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val binding = ItemStudentBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return StudentViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return studentList.size
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        holder.adapterBinding.name.text = studentList[position].studentName
        holder.adapterBinding.textViewRollNo.text = studentList[position].studentRollNo
        holder.adapterBinding.textViewEmail.text = studentList[position].studentEmail

        holder.adapterBinding.constraintLayout.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("studentId", studentList[position]._id)

            val updateStudentFragment = UpdateStudentFragment()
            updateStudentFragment.arguments = bundle

            val appCompatActivity = it.context as AppCompatActivity
            appCompatActivity.supportFragmentManager.beginTransaction()
                    .replace(R.id.frameLayout,updateStudentFragment)
                    .addToBackStack(null)
                    .commit()
        }
    }

    fun getStudentId(position: Int) : String {
        return studentList[position]._id
    }

    fun searchByRoll(searchList : ArrayList<Student>){
        studentList = searchList
        notifyDataSetChanged()
    }
}