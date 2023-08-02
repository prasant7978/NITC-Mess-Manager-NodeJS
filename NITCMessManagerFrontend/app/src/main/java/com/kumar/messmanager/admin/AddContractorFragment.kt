package com.kumar.messmanager.admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.kumar.messmanager.databinding.FragmentAddContractorBinding
import com.kumar.messmanager.model.Contractor
import com.kumar.messmanager.model.Feedback
import com.kumar.messmanager.model.Student

class AddContractorFragment : Fragment() {

    private lateinit var addContractorBinding: FragmentAddContractorBinding

//    val auth : FirebaseAuth = FirebaseAuth.getInstance()
//    val db : FirebaseDatabase = FirebaseDatabase.getInstance()
//    val reference  = db.reference.child("contractors")

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
                    addContractorToDb(
                        contName,
                        contEmail,
                        contPass,
                        messName,
                        costPerDay,
                        foodType,
                        capacity
                    )
                }
            }
        }

        addContractorBinding.buttonClearAll.setOnClickListener {
            clearAllTextArea()
        }

        return addContractorBinding.root
    }

    private fun addContractorToDb(
        contName: String,
        contEmail: String,
        contPass: String,
        messName: String,
        costPerDay: String,
        foodType: String,
        capacity: String
    ) {
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
//            addContractorBinding.buttonAddContractor.isClickable = true
//            addContractorBinding.buttonClearAll.isClickable = true
//            addContractorBinding.progressBar.visibility = View.INVISIBLE
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