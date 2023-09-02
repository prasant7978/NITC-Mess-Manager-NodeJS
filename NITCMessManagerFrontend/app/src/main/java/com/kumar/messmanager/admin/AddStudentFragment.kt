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
import com.kumar.messmanager.databinding.FragmentAddStudentBinding
import com.kumar.messmanager.model.Student
import com.kumar.messmanager.services.AuthenticationServices
import com.kumar.messmanager.services.ProfileService
import com.kumar.messmanager.services.ServiceBuilder
import com.kumar.messmanager.viewmodels.SharedViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddStudentFragment : Fragment() {

    private lateinit var addStudentBinding: FragmentAddStudentBinding
    val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        addStudentBinding = FragmentAddStudentBinding.inflate(inflater,container,false)

        addStudentBinding.buttonAddStudent.setOnClickListener {
            val name = addStudentBinding.textInputName.text.toString()
            val email = addStudentBinding.textInputEmail.text.toString()
            val pass = addStudentBinding.textInputPass.text.toString()
            val roll = addStudentBinding.textInputRoll.text.toString().uppercase()
            val mess = addStudentBinding.textInputMessName.text.toString()

            if(name.isEmpty() || email.isEmpty() ||pass.isEmpty() ||roll.isEmpty()){
                Toast.makeText(activity,"Please provide complete information",Toast.LENGTH_SHORT).show()
            }
            else {
                if(!checkConstraints(email)){
                    Toast.makeText(context,"Enter a valid nitc email id",Toast.LENGTH_SHORT).show()
                }
                else {
                    addStudentBinding.buttonAddStudent.isCheckable = false
                    addStudentBinding.progressBar.visibility = View.VISIBLE

                    showAlertMessageForAdd(name, email, pass, roll, mess)

                    addStudentBinding.buttonAddStudent.isCheckable = true
                    addStudentBinding.progressBar.visibility = View.INVISIBLE
                }
            }
        }

        addStudentBinding.buttonClearAll.setOnClickListener {
            clearAllTextArea()
        }

        return addStudentBinding.root
    }

    private fun addStudentToDb(name: String, email: String, pass: String, roll: String, mess: String){
        val map: HashMap<String, String> = HashMap()
        map["studentEmail"] = email
        map["studentPassword"] = pass
        map["studentName"] = name
        map["studentRollNo"] = roll
        map["messEnrolled"] = mess
        map["userType"] = "Student"

        val profileService: ProfileService = ServiceBuilder.buildService(ProfileService::class.java)
        val requestCall = profileService.addStudent(sharedViewModel.token, map)

        requestCall.enqueue(object: Callback<Boolean>{
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if(response.isSuccessful){
                    Snackbar.make(addStudentBinding.linearLayout,"Student account created",Snackbar.LENGTH_LONG).setAction("close",View.OnClickListener { }).show()
                    clearAllTextArea()
                }
                else if(response.code() == 404 )
                    Toast.makeText(context, "Email address already in use. Please choose another one.", Toast.LENGTH_SHORT).show()
                else
                    Toast.makeText(context, "Server Error", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Log.d("addStudent", t.localizedMessage)
                Toast.makeText(context, t.localizedMessage, Toast.LENGTH_SHORT).show()
            }

        })

        addStudentBinding.buttonAddStudent.isCheckable = true
        addStudentBinding.progressBar.visibility = View.INVISIBLE
    }

    private fun showAlertMessageForAdd(name: String, email: String, pass: String, roll: String, mess: String){
        val dialog = activity?.let { AlertDialog.Builder(it) }
        dialog?.setCancelable(false)
        dialog?.setTitle("Add Student")
        dialog?.setMessage("Are you sure you want to add this student?")
        dialog?.setNegativeButton("No", DialogInterface.OnClickListener{ dialog, which ->
            dialog.cancel()
        })
        dialog?.setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
            addStudentToDb(name, email, pass, roll, mess)
        })
        dialog?.create()?.show()
    }

    fun clearAllTextArea(){
        addStudentBinding.textInputName.setText("")
        addStudentBinding.textInputEmail.setText("")
        addStudentBinding.textInputPass.setText("")
        addStudentBinding.textInputRoll.setText("")
        addStudentBinding.textInputMessName.setText("")
    }

    private fun checkConstraints(email: String): Boolean {
        if(email.contains('_')) {
            val roll = email.substring(email.indexOf("_") + 1, email.length)
            if (roll[0] == 'm' || roll[0] == 'b' || roll[0] == 'p') {
                if(roll.contains('@')) {
                    val domain = roll.substring(roll.indexOf("@") + 1, roll.length)
                    return domain == "nitc.ac.in"
                }
                else{
                    return false
                }
            } else {
                return false
            }
        }
        else{
            return false
        }
    }
}