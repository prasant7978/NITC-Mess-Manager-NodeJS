package com.kumar.messmanager.student

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.kumar.messmanager.databinding.FragmentStudentProfileBinding
import com.kumar.messmanager.model.Student
import com.kumar.messmanager.viewmodels.SharedViewModel

class StudentProfileFragment : Fragment() {

    private lateinit var studentProfileBinding: FragmentStudentProfileBinding
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        studentProfileBinding = FragmentStudentProfileBinding.inflate(inflater,container,false)

        studentProfileBinding.textViewStudentName.isEnabled = false
        studentProfileBinding.textInputStudentEmail.isEnabled = false
        studentProfileBinding.textViewStudentRoll.isEnabled = false
        studentProfileBinding.textViewMessEnrolled.isEnabled = false
        studentProfileBinding.textViewMessBill.isEnabled = false
        studentProfileBinding.textViewPaymentStatus.isEnabled = false

        getAndSet()

        return studentProfileBinding.root
    }

    private fun getAndSet(){
        val student  = sharedViewModel.student
        if(student != null){
            studentProfileBinding.textViewStudentName.setText(student.studentName)
            studentProfileBinding.textInputStudentEmail.setText(student.studentEmail)
            studentProfileBinding.textViewStudentRoll.setText(student.studentRollNo)
            if(student.messEnrolled.isEmpty())
                studentProfileBinding.textViewMessEnrolled.setText("Not enrolled yet")
            else
                studentProfileBinding.textViewMessEnrolled.setText(student.messEnrolled)
            studentProfileBinding.textViewMessBill.setText(student.messBill.toString())
            if(student.paymentStatus == "paid")
                studentProfileBinding.textViewPaymentStatus.setText("Paid")
            else
                studentProfileBinding.textViewPaymentStatus.setText(student.paymentStatus)
        }
    }

}