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
import androidx.core.text.isDigitsOnly
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.kumar.messmanager.R
import com.kumar.messmanager.databinding.FragmentManageContractorBinding
import com.kumar.messmanager.databinding.FragmentUpdateContractorBinding
import com.kumar.messmanager.model.Contractor
import com.kumar.messmanager.model.Feedback
import com.kumar.messmanager.model.Student
import com.kumar.messmanager.services.ProfileService
import com.kumar.messmanager.services.ServiceBuilder
import com.kumar.messmanager.viewmodels.SharedViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateContractorFragment : Fragment() {
    private lateinit var updateContractorBinding: FragmentUpdateContractorBinding
    val sharedViewModel: SharedViewModel by activityViewModels()

    private var contractorId = ""
    lateinit var contractor : Contractor
    private var slotFilled = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        updateContractorBinding = FragmentUpdateContractorBinding.inflate(inflater,container,false)

        retrieveDetailsFromDb()

        updateContractorBinding.textInputAvailability.isEnabled = false

        updateContractorBinding.buttonUpdateContractor.setOnClickListener {

            val costPerDay = updateContractorBinding.textInputCostPerDay.text.toString()
            val capacity = updateContractorBinding.textInputCapacity.text.toString()

            if(costPerDay.toIntOrNull() == null){
                Snackbar.make(updateContractorBinding.linearLayout,"Please enter numerical value for cost per day.",Snackbar.LENGTH_LONG).setAction("close",View.OnClickListener { }).show()
            }
            else if(capacity.toIntOrNull() == null){
                Snackbar.make(updateContractorBinding.linearLayout,"Please enter numerical value for capacity.",Snackbar.LENGTH_LONG).setAction("close",View.OnClickListener { }).show()
            }
            else if(costPerDay.toInt() <= 0){
                Snackbar.make(updateContractorBinding.linearLayout,"Cost per day can't be zero or negative",Snackbar.LENGTH_LONG).setAction("close",View.OnClickListener { }).show()
            }
            else if(capacity.toInt() <= 0){
                Snackbar.make(updateContractorBinding.linearLayout,"Capacity can't be zero or negative",Snackbar.LENGTH_LONG).setAction("close",View.OnClickListener { }).show()
            }
            else {
                showAlertMessageForUpdate()
            }
        }

        updateContractorBinding.buttonDeleteContractor.setOnClickListener {
            showAlertMessageForDelete()
        }

        return updateContractorBinding.root
    }

    private fun showAlertMessageForUpdate() {
        val dialog = activity?.let { AlertDialog.Builder(it) }
        dialog?.setCancelable(false)
        dialog?.setTitle("Delete Contractor")
        dialog?.setMessage("Are you sure you want to update this contractor ?")
        dialog?.setNegativeButton("No", DialogInterface.OnClickListener{ dialog, which ->
            dialog.cancel()
        })
        dialog?.setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
            updateContractorDetails()
        })
        dialog?.create()?.show()
    }

    private fun showAlertMessageForDelete() {
        val dialog = activity?.let { AlertDialog.Builder(it) }
        dialog?.setCancelable(false)
        dialog?.setTitle("Delete Contractor")
        dialog?.setMessage("Are you sure you want to delete this contractor ?")
        dialog?.setNegativeButton("No", DialogInterface.OnClickListener{ dialog, which ->
            dialog.cancel()
        })
        dialog?.setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
            deleteContractor()
        })
        dialog?.create()?.show()
    }

    private fun deleteContractor() {
        val profileService: ProfileService = ServiceBuilder.buildService(ProfileService::class.java)
        val requestCall = profileService.deleteContractor(sharedViewModel.token, contractorId)

        requestCall.enqueue(object: Callback<Boolean>{
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if(response.isSuccessful){
                    if(response.body() == true){
                        clearAllTextArea()
                        Snackbar.make(updateContractorBinding.linearLayout,"The contractor has been deleted",Snackbar.LENGTH_LONG).setAction("close",View.OnClickListener { }).show()
                        requireActivity().supportFragmentManager.popBackStack()
                    }
                    else
                        Snackbar.make(updateContractorBinding.linearLayout,"The deletion was not successful, Please try again!",Snackbar.LENGTH_LONG).setAction("close",View.OnClickListener { }).show()
                }
                else
                    Toast.makeText(context, "Server Error", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Log.d("deleteContractor", t.localizedMessage)
                Toast.makeText(context, t.localizedMessage, Toast.LENGTH_SHORT).show()
            }

        })

//        reference.child(uid).removeValue().addOnCompleteListener { task ->
//            if(task.isSuccessful){
//                reference.orderByChild("studentId").equalTo(uid).addListenerForSingleValueEvent(object : ValueEventListener{
//                    override fun onDataChange(snapshot: DataSnapshot) {
//                        for(ds in snapshot.children){
//                            contractor = ds.getValue(Contractor::class.java)!!
//                        }
//                    }
//
//                    override fun onCancelled(error: DatabaseError) {
//                        TODO("Not yet implemented")
//                    }
//
//                })
//
//                clearAllTextArea()
//                Snackbar.make(updateContractorBinding.linearLayout,"The contractor has been deleted",Snackbar.LENGTH_LONG).setAction("close",View.OnClickListener { }).show()
//                requireActivity().supportFragmentManager.popBackStack()
//            }
//            else{
//                Snackbar.make(updateContractorBinding.linearLayout,"The deletion was not successful, Please try again!",Snackbar.LENGTH_LONG).setAction("close",View.OnClickListener { }).show()
//            }
//        }
    }

    private fun clearAllTextArea() {
        updateContractorBinding.textInputContractorName.setText("")
        updateContractorBinding.textInputContractorEmail.setText("")
        updateContractorBinding.textInputContractorPass.setText("")
        updateContractorBinding.textInputMessName.setText("")
        updateContractorBinding.textInputCostPerDay.setText("")
        updateContractorBinding.textInputFoodType.setText("")
        updateContractorBinding.textInputCapacity.setText("")
        updateContractorBinding.textInputAvailability.setText("")

    }

    private fun updateContractorDetails() {
        updateContractorBinding.buttonUpdateContractor.isClickable = false
        updateContractorBinding.buttonDeleteContractor.isClickable = false
        updateContractorBinding.progressBar.visibility = View.VISIBLE

        val map = HashMap<String,Any>()
        map["contractorId"] = contractorId
        map["contractorEmail"] = updateContractorBinding.textInputContractorEmail.text.toString()
        map["contractorPassword"] = updateContractorBinding.textInputContractorPass.text.toString()
        map["contractorName"] = updateContractorBinding.textInputContractorName.text.toString()
        map["costPerDay"] = updateContractorBinding.textInputCostPerDay.text.toString().toInt()
        map["messName"] = updateContractorBinding.textInputMessName.text.toString()
        map["foodType"] = updateContractorBinding.textInputFoodType.text.toString()
        map["capacity"] = updateContractorBinding.textInputCapacity.text.toString().toInt()

        val updatedAvailability = updateContractorBinding.textInputCapacity.text.toString().toInt() - slotFilled
        map["availability"] = updatedAvailability

        val profileService: ProfileService = ServiceBuilder.buildService(ProfileService::class.java)
        val requestCall = profileService.adminUpdateContractorProfile(map, sharedViewModel.token)

        requestCall.enqueue(object: Callback<Boolean>{
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if(response.isSuccessful){
                    retrieveDetailsFromDb()
                    Snackbar.make(updateContractorBinding.linearLayout,"The contractor has been updated successfully", Snackbar.LENGTH_LONG).setAction("Close", View.OnClickListener { }).show()
                }
                else
                    Toast.makeText(context, "Server Error", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Log.d("updateStudent", t.localizedMessage)
                Toast.makeText(context, t.localizedMessage, Toast.LENGTH_SHORT).show()
            }

        })

//        map["feedbackReceived"] = adminViewModel.feedbackList
//        map["studentEnrolled"] = adminViewModel.studentEnrolledList

        updateContractorBinding.buttonUpdateContractor.isClickable = true
        updateContractorBinding.buttonDeleteContractor.isClickable = true
        updateContractorBinding.progressBar.visibility = View.INVISIBLE
    }

    private fun retrieveDetailsFromDb() {
        contractorId = arguments?.getString("contractorId").toString()

        val profileService: ProfileService = ServiceBuilder.buildService(ProfileService::class.java)
        val requestCall = profileService.getContractorProfileWithId(sharedViewModel.token, contractorId)

       requestCall.enqueue(object: Callback<Contractor>{
           override fun onResponse(call: Call<Contractor>, response: Response<Contractor>) {
               if(response.isSuccessful){
                   val contractor = response.body()
                   if(contractor != null){
                       updateContractorBinding.textInputContractorName.setText(contractor.contractorName)
                       updateContractorBinding.textInputContractorEmail.setText(contractor.contractorEmail)
                       updateContractorBinding.textInputContractorPass.setText(contractor.contractorPassword)
                       updateContractorBinding.textInputMessName.setText(contractor.messName)
                       updateContractorBinding.textInputCostPerDay.setText(contractor.costPerDay.toString())
                       updateContractorBinding.textInputFoodType.setText(contractor.foodType)
                       updateContractorBinding.textInputCapacity.setText(contractor.capacity.toString())
                       updateContractorBinding.textInputAvailability.setText(contractor.availability.toString())
                       slotFilled = contractor.capacity - contractor.availability
                   }
                   else{
                       Toast.makeText(context, "Contractor not found", Toast.LENGTH_SHORT).show()
                       clearAllTextArea()
                   }
               }
               else
                   Toast.makeText(context, "Server Error", Toast.LENGTH_SHORT).show()
           }

           override fun onFailure(call: Call<Contractor>, t: Throwable) {
               Log.d("getContractorDetails", t.localizedMessage)
               Toast.makeText(context, t.localizedMessage, Toast.LENGTH_SHORT).show()
           }

       })
    }

}