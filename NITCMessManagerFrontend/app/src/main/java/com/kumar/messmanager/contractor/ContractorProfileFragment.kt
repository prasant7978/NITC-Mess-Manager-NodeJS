package com.kumar.messmanager.contractor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.material.snackbar.Snackbar
import com.kumar.messmanager.databinding.FragmentContractorProfileBinding
import com.kumar.messmanager.model.Contractor
import com.kumar.messmanager.viewmodels.SharedViewModel

class ContractorProfileFragment : Fragment() {

    private lateinit var contractorProfileBinding: FragmentContractorProfileBinding
    private val sharedViewModel: SharedViewModel by activityViewModels()

    private var uid = ""
    lateinit var contractor : Contractor
    private var slotFilled = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        contractorProfileBinding = FragmentContractorProfileBinding.inflate(inflater,container,false)

        retrieveDetailsFromDb()

        contractorProfileBinding.textInputAvailability.isEnabled = false
        contractorProfileBinding.textInputContractorEmail.isEnabled = false
        contractorProfileBinding.textInputContractorPass.isEnabled = false

        contractorProfileBinding.buttonUpdateContractor.setOnClickListener {

            val costPerDay = contractorProfileBinding.textInputCostPerDay.text.toString()
            val capacity = contractorProfileBinding.textInputCapacity.text.toString()

            if(costPerDay.toIntOrNull() == null){
                Snackbar.make(contractorProfileBinding.linearLayout,"Please enter numerical value for cost per day.",Snackbar.LENGTH_LONG).setAction("close",View.OnClickListener { }).show()
            }
            else if(capacity.toIntOrNull() == null){
                Snackbar.make(contractorProfileBinding.linearLayout,"Please enter numerical value for capacity.",Snackbar.LENGTH_LONG).setAction("close",View.OnClickListener { }).show()
            }
            else if(capacity.toInt() <= 0){
                Snackbar.make(contractorProfileBinding.linearLayout,"Capacity can't be zero or negative",Snackbar.LENGTH_SHORT).setAction("close",View.OnClickListener {  }).show()
            }
            else if(costPerDay.toInt() <= 0){
                Snackbar.make(contractorProfileBinding.linearLayout,"Cost per day can't be zero or negative",Snackbar.LENGTH_SHORT).setAction("close",View.OnClickListener {  }).show()
            }
            else {
//                updateContractorDetails()
            }
        }

        return contractorProfileBinding.root
    }

    private fun retrieveDetailsFromDb() {
        val contractor = sharedViewModel.contractor

        contractorProfileBinding.textInputContractorName.setText(contractor.contractorName)
        contractorProfileBinding.textInputContractorEmail.setText(contractor.contractorEmail)
        contractorProfileBinding.textInputContractorPass.setText(contractor.contractorPassword)
        contractorProfileBinding.textInputMessName.setText(contractor.messName)
        contractorProfileBinding.textInputCostPerDay.setText(contractor.costPerDay.toString())
        contractorProfileBinding.textInputFoodType.setText(contractor.foodType)
        contractorProfileBinding.textInputCapacity.setText(contractor.capacity.toString())
        contractorProfileBinding.textInputAvailability.setText(contractor.availability.toString())

        slotFilled = contractor.capacity - contractor.availability
    }

    private fun updateContractorDetails() {
        contractorProfileBinding.buttonUpdateContractor.isClickable = false
        contractorProfileBinding.progressBar.visibility = View.VISIBLE

        val map = mutableMapOf<String,Any>()
        map["contractorId"] = uid
        map["contractorEmail"] = contractorProfileBinding.textInputContractorEmail.text.toString()
        map["contractorPassword"] = contractorProfileBinding.textInputContractorPass.text.toString()
        map["userType"] = "Contractor"
        map["contractorName"] = contractorProfileBinding.textInputContractorName.text.toString()
        map["costPerDay"] = contractorProfileBinding.textInputCostPerDay.text.toString().toInt()
        map["messName"] = contractorProfileBinding.textInputMessName.text.toString()
        map["foodType"] = contractorProfileBinding.textInputFoodType.text.toString()
        map["capacity"] = contractorProfileBinding.textInputCapacity.text.toString().toInt()

        val updatedAvailability = contractorProfileBinding.textInputCapacity.text.toString().toInt() - slotFilled

        map["availability"] = updatedAvailability

//        reference.child(uid).updateChildren(map).addOnCompleteListener { task ->
//            if(task.isSuccessful){
//                retrieveDetailsFromDb(uid)
//                Snackbar.make(contractorProfileBinding.linearLayout,"Your profile has been updated successfully", Snackbar.LENGTH_LONG).setAction("Close", View.OnClickListener { }).show()
//            }
//            contractorProfileBinding.buttonUpdateContractor.isClickable = true
//            contractorProfileBinding.progressBar.visibility = View.INVISIBLE
//        }

    }

}