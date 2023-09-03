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
import com.kumar.messmanager.databinding.FragmentAddContractorBinding
import com.kumar.messmanager.model.Contractor
import com.kumar.messmanager.model.Feedback
import com.kumar.messmanager.model.Student
import com.kumar.messmanager.services.ProfileService
import com.kumar.messmanager.services.ServiceBuilder
import com.kumar.messmanager.viewmodels.SharedViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddContractorFragment : Fragment() {

    private lateinit var addContractorBinding: FragmentAddContractorBinding
    val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        addContractorBinding = FragmentAddContractorBinding.inflate(inflater,container,false)

        addContractorBinding.buttonAddContractor.setOnClickListener {

            val contName = addContractorBinding.textInputContractorName.text.toString()
            val contEmail = addContractorBinding.textInputContractorEmail.text.toString()
            val contPass = addContractorBinding.textInputContractorPass.text.toString()
            val messName = addContractorBinding.textInputMessName.text.toString()
            val costPerDay = addContractorBinding.textInputCostPerDay.text.toString()
            val foodType = addContractorBinding.textInputFoodType.text.toString()
            val capacity = addContractorBinding.textInputCapacity.text.toString()

            if(contName.isEmpty() || contEmail.isEmpty() || contPass.isEmpty() || messName.isEmpty() || costPerDay.isEmpty() || foodType.isEmpty() || capacity.isEmpty()){
                Toast.makeText(activity,"Please provide complete information", Toast.LENGTH_SHORT).show()
            }
            else{
                if(costPerDay.toIntOrNull() == null){
                    Snackbar.make(addContractorBinding.linearLayout,"Please enter numerical value for cost per day.",Snackbar.LENGTH_LONG).setAction("close",View.OnClickListener { }).show()
                }
                else if(capacity.toIntOrNull() == null){
                    Snackbar.make(addContractorBinding.linearLayout,"Please enter numerical value for capacity.",Snackbar.LENGTH_LONG).setAction("close",View.OnClickListener { }).show()
                }
                else if(costPerDay.toInt() <= 0){
                    Toast.makeText(activity,"Cost per day can't be zero or negative", Toast.LENGTH_SHORT).show()
                }
                else if(capacity.toInt() <= 0){
                    Toast.makeText(activity,"Capacity can't be zero or negative", Toast.LENGTH_SHORT).show()
                }
                else {
                    addContractorBinding.buttonAddContractor.isClickable = false
                    addContractorBinding.buttonClearAll.isClickable = false
                    addContractorBinding.progressBar.visibility = View.VISIBLE

                    showAlertMessageForAdd(
                        contName,
                        contEmail,
                        contPass,
                        messName,
                        costPerDay,
                        foodType,
                        capacity
                    )

                    addContractorBinding.buttonAddContractor.isClickable = true
                    addContractorBinding.buttonClearAll.isClickable = true
                    addContractorBinding.progressBar.visibility = View.INVISIBLE
                }
            }
        }

        addContractorBinding.buttonClearAll.setOnClickListener {
            clearAllTextArea()
        }

        return addContractorBinding.root
    }

    private fun showAlertMessageForAdd(name: String, email: String, pass: String, mess: String, costPerDay: String, foodType: String, capacity: String){
        val dialog = activity?.let { AlertDialog.Builder(it) }
        dialog?.setCancelable(false)
        dialog?.setTitle("Add Contractor")
        dialog?.setMessage("Are you sure you want to add this contractor?")
        dialog?.setNegativeButton("No", DialogInterface.OnClickListener{ dialog, which ->
            dialog.cancel()
        })
        dialog?.setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
            addContractorToDb(name, email, pass, mess, costPerDay, foodType, capacity)
        })
        dialog?.create()?.show()
    }

    private fun addContractorToDb(contName: String, contEmail: String, contPass: String, messName: String, costPerDay: String, foodType: String, capacity: String) {
        val map: HashMap<String, Any> = HashMap()
        map["contractorEmail"] = contEmail
        map["contractorPassword"] = contPass
        map["contractorName"] = contName
        map["messName"] = messName
        map["costPerDay"] = costPerDay.toInt()
        map["foodType"] = foodType
        map["capacity"] = capacity.toInt()
        map["availability"] = capacity.toInt()
        map["userType"] = "Contractor"

        val profileService: ProfileService = ServiceBuilder.buildService(ProfileService::class.java)
        val requestCall = profileService.addContractor(sharedViewModel.token, map)

        requestCall.enqueue(object: Callback<Boolean> {
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if(response.isSuccessful){
                    Snackbar.make(addContractorBinding.linearLayout,"Contractor account created",Snackbar.LENGTH_LONG).setAction("close",View.OnClickListener { }).show()
                    clearAllTextArea()
                }
                else if(response.code() == 404 )
                    Toast.makeText(context, "Email address already in use. Please choose another one.", Toast.LENGTH_SHORT).show()
                else
                    Toast.makeText(context, "Server Error", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Log.d("addContractor", t.localizedMessage)
                Toast.makeText(context, t.localizedMessage, Toast.LENGTH_SHORT).show()
            }

        })

//        auth.createUserWithEmailAndPassword(contEmail,contPass).addOnCompleteListener { task ->
//            if(task.isSuccessful){
//                val uid = task.result.user?.uid.toString()
//                val feedBackList  = ArrayList<Feedback>()
//                val studentEnrolledList  = ArrayList<Student>()
//                val contractor = Contractor(uid,contEmail,contPass,"Contractor",contName,costPerDay.toInt(),messName,foodType,capacity.toInt(),capacity.toInt(),0)
//
//                reference.child(uid).setValue(contractor)
//
//                Snackbar.make(addContractorBinding.linearLayout,"Contractor account created", Snackbar.LENGTH_LONG).setAction("close",View.OnClickListener { }).show()
//                clearAllTextArea()
//            }else{
//                Toast.makeText(activity,task.exception?.localizedMessage,Toast.LENGTH_LONG).show()
//            }
//        }
    }

    private fun clearAllTextArea() {
        addContractorBinding.textInputContractorName.setText("")
        addContractorBinding.textInputContractorEmail.setText("")
        addContractorBinding.textInputContractorPass.setText("")
        addContractorBinding.textInputMessName.setText("")
        addContractorBinding.textInputCostPerDay.setText("")
        addContractorBinding.textInputFoodType.setText("")
        addContractorBinding.textInputCapacity.setText("")
    }

}