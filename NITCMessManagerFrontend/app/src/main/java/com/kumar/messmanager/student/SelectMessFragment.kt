package com.kumar.messmanager.student

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import com.google.android.material.snackbar.Snackbar
import com.kumar.messmanager.R
import com.kumar.messmanager.contractor.ManageMessMenuFragment
import com.kumar.messmanager.contractor.access.GetProfileAccess
import com.kumar.messmanager.databinding.FragmentSelectMessBinding
import com.kumar.messmanager.services.ProfileService
import com.kumar.messmanager.services.ServiceBuilder
import com.kumar.messmanager.viewmodels.SharedViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SelectMessFragment : Fragment() {

    private lateinit var selectMessBinding: FragmentSelectMessBinding
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private var messName : String = " "

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        selectMessBinding = FragmentSelectMessBinding.inflate(inflater,container,false)

        receiveMessDetails()

        selectMessBinding.progressBar.visibility = View.INVISIBLE

        selectMessBinding.textInputMessName.isEnabled = false
        selectMessBinding.textInputFoodType.isEnabled = false
        selectMessBinding.textInputCostPerDay.isEnabled = false
        selectMessBinding.textInputAvailability.isEnabled = false

        selectMessBinding.buttonShowMenu.setOnClickListener {
            val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
            val manageMessMenuFragment = ManageMessMenuFragment()

            val bundle = Bundle()
            bundle.putString("messName", messName)

            manageMessMenuFragment.arguments = bundle

            fragmentTransaction.replace(R.id.frameLayout, manageMessMenuFragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        selectMessBinding.buttonSelectMess.setOnClickListener {
            val aval = selectMessBinding.textInputAvailability.text.toString().toInt()
            if(aval == 0){
                Snackbar.make(selectMessBinding.constraintSelectMessLayout,"This mess is full.",
                    Snackbar.LENGTH_LONG).setAction("Close", View.OnClickListener { }).show()
            }
            else {
                val messEnrolled = sharedViewModel.student.messEnrolled
                if (messEnrolled == "") {
                    val dialog = AlertDialog.Builder(activity)
                    dialog.setTitle("Are you sure?")
                    dialog.setCancelable(false)
                    dialog.setMessage("Once you enroll, you can't change it unless mess bill is generated")
                    dialog.setNegativeButton(
                        "No",
                        DialogInterface.OnClickListener { dialog, which ->
                            dialog.cancel()
                        })
                    dialog.setPositiveButton(
                        "Yes",
                        DialogInterface.OnClickListener { dialog, which ->
                            selectMessBinding.buttonSelectMess.isClickable = false
                            selectMessBinding.progressBar.visibility = View.VISIBLE
                            addMessToStudentDb()
                        })
                    dialog.create().show()
                }
                else {
                    val dialog = AlertDialog.Builder(activity)
                    dialog.setTitle("Select Mess")
                    dialog.setCancelable(false)
                    dialog.setMessage("You have already enrolled in $messEnrolled for this month")
                    dialog.setNegativeButton("OK", DialogInterface.OnClickListener { dialog, which ->
                            dialog.cancel()
                        })
                    dialog.create().show()
                }

//                val studentUid = FirebaseAuth.getInstance().currentUser?.uid.toString()
//                reference_student.orderByChild("studentId").equalTo(studentUid)
//                    .addListenerForSingleValueEvent(object :
//                        ValueEventListener {
//                        override fun onDataChange(snapshot: DataSnapshot) {
//                            for (ds in snapshot.children) {
//                                Log.d("debug", ds.child("messEnrolled").value.toString())
//                                var messEnrolled = ds.child("messEnrolled").value.toString()
//
//                                if (messEnrolled == "") {
//                                    val dialog = AlertDialog.Builder(activity)
//                                    dialog.setTitle("Are you sure?")
//                                    dialog.setCancelable(false)
//                                    dialog.setMessage("Once you enroll, you can't change it unless mess bill is generated")
//                                    dialog.setNegativeButton(
//                                        "No",
//                                        DialogInterface.OnClickListener { dialog, which ->
//                                            dialog.cancel()
//                                        })
//                                    dialog.setPositiveButton(
//                                        "Yes",
//                                        DialogInterface.OnClickListener { dialog, which ->
//                                            selectMessBinding.buttonSelectMess.isClickable = false
//                                            selectMessBinding.progressBar.visibility = View.VISIBLE
//                                            addMessToStudentDb(studentUid.toString())
//                                        })
//                                    dialog.create().show()
//                                } else {
//                                    val dialog = AlertDialog.Builder(activity)
//                                    dialog.setTitle("Select Mess")
//                                    dialog.setCancelable(false)
//                                    dialog.setMessage("You have already enrolled in $messEnrolled for this month")
//                                    dialog.setNegativeButton(
//                                        "OK",
//                                        DialogInterface.OnClickListener { dialog, which ->
//                                            dialog.cancel()
//                                        })
//                                    dialog.create().show()
//                                }
//                            }
//                        }
//
//                        override fun onCancelled(error: DatabaseError) {
//                            TODO("Not yet implemented")
//                        }
//
//                    })
            }
        }

        return selectMessBinding.root
    }

    private fun addMessToStudentDb() {
        val profileServices: ProfileService = ServiceBuilder.buildService(ProfileService::class.java)
        val requestCall = profileServices.addMessNameToStudentProfile(messName, sharedViewModel.token)

        requestCall.enqueue(object :Callback<Boolean>{
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if(response.isSuccessful){
                    Snackbar.make(selectMessBinding.constraintSelectMessLayout,"You have successfully enrolled in $messName", Snackbar.LENGTH_LONG).setAction("Close", View.OnClickListener { }).show()
                    val fragmentManager : FragmentManager = requireActivity().supportFragmentManager
                    val fragmentTransaction : FragmentTransaction = fragmentManager.beginTransaction()
                    val studentDashboardFragment = StudentDashboardFragment()

                    fragmentTransaction.replace(R.id.frameLayout,studentDashboardFragment)
                    fragmentTransaction.addToBackStack(null)
                    fragmentTransaction.commit()
//                    updateAvailabilityInCardViewFromDb()
                }
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Toast.makeText(context, t.localizedMessage, Toast.LENGTH_SHORT).show()
            }

        })


//        reference_student.child(studentUid).updateChildren(map).addOnCompleteListener { task ->
//            if(task.isSuccessful){
//
//                var student : Student = Student()
//                reference_student.child(studentUid).addListenerForSingleValueEvent(object : ValueEventListener{
//                    override fun onDataChange(snapshot: DataSnapshot) {
//                        student = snapshot.getValue(Student::class.java)!!
//                        Log.d("student",student.studentName+" "+student.messEnrolled)
//
//                        reference_conttractor.orderByChild("contractorId").equalTo(contractorUid)
//                            .addListenerForSingleValueEvent(object : ValueEventListener {
//                                override fun onDataChange(snapshot: DataSnapshot) {
//                                    for(ds in snapshot.children){
//                                        val cont : Contractor = ds.getValue(Contractor::class.java)!!
//                                        cont.availability = cont.availability.toString().toInt() - 1
//                                        cont.studentEnrolled.add(student)
//                                        reference_conttractor.child(cont.contractorId).setValue(cont)
//                                        Snackbar.make(selectMessBinding.constraintSelectMessLayout,"You have successfully enrolled in ${student.messEnrolled}",
//                                            Snackbar.LENGTH_LONG).setAction("Close", View.OnClickListener { }).show()
//
//                                        updateAvailabilityInCardViewFromDb()
//                                    }
//                                }
//
//                                override fun onCancelled(error: DatabaseError) {
//                                    TODO("Not yet implemented")
//                                }
//
//                            })
//                    }
//
//                    override fun onCancelled(error: DatabaseError) {
//                        TODO("Not yet implemented")
//                    }
//
//                })
//
//            }
//        }

        selectMessBinding.buttonSelectMess.isClickable = true
        selectMessBinding.progressBar.visibility = View.INVISIBLE
    }

//    private fun updateAvailabilityInCardViewFromDb() {
//
//
//        reference_conttractor.orderByChild("contractorId").equalTo(contractorUid).addValueEventListener(object : ValueEventListener{
//            override fun onDataChange(snapshot: DataSnapshot) {
//                for(ds in snapshot.children){
//                    selectMessBinding.textInputAvailability.setText(ds.child("availability").value.toString())
//                }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
//            }
//
//        })
//    }

    private fun receiveMessDetails() {
        messName = arguments?.getString("messName").toString()
        selectMessBinding.textInputMessName.setText(arguments?.getString("messName").toString())
        selectMessBinding.textInputFoodType.setText(arguments?.getString("foodType").toString())
        selectMessBinding.textInputCostPerDay.setText(arguments?.getString("costPerDay").toString())
        selectMessBinding.textInputAvailability.setText(arguments?.getString("availability").toString())
    }

}