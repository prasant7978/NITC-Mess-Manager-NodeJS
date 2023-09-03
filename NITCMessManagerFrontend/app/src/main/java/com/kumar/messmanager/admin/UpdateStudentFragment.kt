package com.kumar.messmanager.admin

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import com.google.android.material.snackbar.Snackbar
import com.kumar.messmanager.databinding.FragmentUpdateStudentBinding
import com.kumar.messmanager.model.Student
import com.kumar.messmanager.services.ProfileService
import com.kumar.messmanager.services.ServiceBuilder
import com.kumar.messmanager.viewmodels.SharedViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateStudentFragment : Fragment() {

    private lateinit var updateStudentBinding: FragmentUpdateStudentBinding
    val sharedViewModel: SharedViewModel by activityViewModels()

    private var messBill : Int = 0
    private var paymentStatus : String = ""
    private var studentId : String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        updateStudentBinding = FragmentUpdateStudentBinding.inflate(inflater,container,false)

        updateStudentBinding.textInputMessName.isEnabled = false

        retrieveDetailsFromDb()

        updateStudentBinding.buttonUpdateStudent.setOnClickListener {
            updateStudentBinding.buttonUpdateStudent.isClickable = false
            updateStudentBinding.buttonDeleteStudent.isClickable = false
            updateStudentBinding.progressBar.visibility = View.VISIBLE

            showAlertMessageForUpdate()

            updateStudentBinding.buttonUpdateStudent.isClickable = true
            updateStudentBinding.buttonDeleteStudent.isClickable = true
            updateStudentBinding.progressBar.visibility = View.INVISIBLE
        }

        updateStudentBinding.buttonDeleteStudent.setOnClickListener {
            showAlertMessageForDelete()
        }

        return updateStudentBinding.root
    }

    private fun updateStudentDetails(){
        val map = HashMap<String,Any>()

        map["studentId"] = studentId
        map["studentName"] = updateStudentBinding.textInputName.text.toString()
        map["studentEmail"] = updateStudentBinding.textInputEmail.text.toString()
        map["studentPassword"] = updateStudentBinding.textInputPass.text.toString()
        map["studentRollNo"] = updateStudentBinding.textInputRoll.text.toString()

        val profileService: ProfileService = ServiceBuilder.buildService(ProfileService::class.java)
        val requestCall = profileService.updateStudentProfile(sharedViewModel.token, map)

        requestCall.enqueue(object: Callback<Boolean>{
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if(response.isSuccessful){
                    retrieveDetailsFromDb()
                    Snackbar.make(updateStudentBinding.linearLayout,"The user has been updated successfully", Snackbar.LENGTH_LONG).setAction("Close", View.OnClickListener { }).show()
                }
                else
                    Toast.makeText(context, "Server Error", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Log.d("updateStudent", t.localizedMessage)
                Toast.makeText(context, t.localizedMessage, Toast.LENGTH_SHORT).show()
            }

        })

        updateStudentBinding.buttonUpdateStudent.isClickable = true
        updateStudentBinding.buttonDeleteStudent.isClickable = true
        updateStudentBinding.progressBar.visibility = View.INVISIBLE
    }

    private fun retrieveDetailsFromDb(){
        studentId = arguments?.getString("studentId").toString()

        val profileService: ProfileService = ServiceBuilder.buildService(ProfileService::class.java)
        val requestCall = profileService.getStudentProfileWithId(sharedViewModel.token, studentId)

        requestCall.enqueue(object: Callback<Student>{
            override fun onResponse(call: Call<Student>, response: Response<Student>) {
                if(response.isSuccessful){
                    val student = response.body()
                    if(student != null){
                        updateStudentBinding.textInputName.setText(student.studentName)
                        updateStudentBinding.textInputEmail.setText(student.studentEmail)
                        updateStudentBinding.textInputRoll.setText(student.studentRollNo)
                        updateStudentBinding.textInputPass.setText(student.studentPassword)

                        val messName = student.messEnrolled
                        if(messName != "")
                            updateStudentBinding.textInputMessName.setText(messName)
                        else
                            updateStudentBinding.textInputMessName.setText("Not Enrolled Yet")

                        messBill = student.messBill
                        paymentStatus = student.paymentStatus
                    }
                    else{
                        Toast.makeText(context, "Student not found", Toast.LENGTH_SHORT).show()
                        clearAllTextArea()
                    }
                }
                else
                    Toast.makeText(context, "Server Error", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<Student>, t: Throwable) {
                Log.d("getStudentDetails", t.localizedMessage)
                Toast.makeText(context, t.localizedMessage, Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun deleteStudent(){
        val profileService: ProfileService = ServiceBuilder.buildService(ProfileService::class.java)
        val requestCall = profileService.deleteStudent(sharedViewModel.token, studentId)

        requestCall.enqueue(object: Callback<Boolean>{
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if(response.isSuccessful){
                    if(response.body() == true){
                        clearAllTextArea()
                        Snackbar.make(updateStudentBinding.linearLayout,"The student has been deleted",Snackbar.LENGTH_LONG).setAction("close",View.OnClickListener { }).show()
                        requireActivity().supportFragmentManager.popBackStack()
                    }
                    else
                        Snackbar.make(updateStudentBinding.linearLayout,"The deletion was not successful, Please try again!",Snackbar.LENGTH_LONG).setAction("close",View.OnClickListener { }).show()
                }
                else
                    Toast.makeText(context, "Server Error", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Log.d("deleteStudent", t.localizedMessage)
                Toast.makeText(context, t.localizedMessage, Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun showAlertMessageForUpdate(){
        val dialog = activity?.let { AlertDialog.Builder(it) }
        dialog?.setCancelable(false)
        dialog?.setTitle("Update Student")
        dialog?.setMessage("Are you sure you want to update this student?")
        dialog?.setNegativeButton("No", DialogInterface.OnClickListener{ dialog, which ->
            dialog.cancel()
        })
        dialog?.setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
            updateStudentDetails()
        })
        dialog?.create()?.show()
    }

    private fun showAlertMessageForDelete(){
        val dialog = activity?.let { AlertDialog.Builder(it) }
        dialog?.setCancelable(false)
        dialog?.setTitle("Delete Student")
        dialog?.setMessage("Are you sure you want to delete this student?")
        dialog?.setNegativeButton("No", DialogInterface.OnClickListener{ dialog, which ->
            dialog.cancel()
        })
        dialog?.setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
            deleteStudent()
        })
        dialog?.create()?.show()
    }

    fun clearAllTextArea(){
        updateStudentBinding.textInputName.setText("")
        updateStudentBinding.textInputEmail.setText("")
        updateStudentBinding.textInputPass.setText("")
        updateStudentBinding.textInputRoll.setText("")
        updateStudentBinding.textInputMessName.setText("")
    }
}