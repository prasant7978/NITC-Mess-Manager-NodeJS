package com.kumar.messmanager.contractor

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
import com.kumar.messmanager.databinding.FragmentSingleFeedbackBinding
import com.kumar.messmanager.model.Contractor
import com.kumar.messmanager.services.FeedbackServices
import com.kumar.messmanager.services.ServiceBuilder
import com.kumar.messmanager.viewmodels.SharedViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SingleFeedbackFragment : Fragment() {

    private lateinit var singleFeedbackBinding: FragmentSingleFeedbackBinding
    val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        singleFeedbackBinding = FragmentSingleFeedbackBinding.inflate(inflater,container,false)

        receiveDetailsFromRecyclerView()

        singleFeedbackBinding.buttonDelete.setOnClickListener {
            showAlertMessage()
        }

        return singleFeedbackBinding.root
    }

    private fun showAlertMessage() {
        val dialog = activity?.let { AlertDialog.Builder(it) }
        dialog?.setCancelable(false)
        dialog?.setTitle("Delete Feedback")
        dialog?.setMessage("Are you sure you want to delete this feedback ?")
        dialog?.setNegativeButton("No", DialogInterface.OnClickListener{ dialog, which ->
            dialog.cancel()
        })
        dialog?.setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
            deleteFeedback()
        })
        dialog?.create()?.show()
    }

    private fun deleteFeedback() {
        val feedbackServices: FeedbackServices = ServiceBuilder.buildService(FeedbackServices::class.java)
        val requestCall = feedbackServices.deleteFeedback(sharedViewModel.token, sharedViewModel.feedback._id.toString())

        requestCall.enqueue(object: Callback<Boolean>{
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if(response.isSuccessful){
                    if(response.body() != null){
                        if(response.body() == true){
                            Snackbar.make(singleFeedbackBinding.constraintLayout,"The feedback has been deleted",
                                Snackbar.LENGTH_LONG).setAction("close",View.OnClickListener { }).show()
                            requireActivity().supportFragmentManager.popBackStack()
                        }
                        else{
                            Snackbar.make(singleFeedbackBinding.constraintLayout,"The deletion was not successful, Please try again!",
                                Snackbar.LENGTH_LONG).setAction("close",View.OnClickListener { }).show()
                        }
                    }
                }
                else
                    Toast.makeText(context, "Server Error", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Log.d("failure", t.localizedMessage)
                Toast.makeText(context, t.localizedMessage, Toast.LENGTH_SHORT).show()
            }

        })


//        val contractorId = FirebaseAuth.getInstance().currentUser?.uid.toString()
//        reference.child(contractorId).addListenerForSingleValueEvent(object:ValueEventListener{
//            override fun onDataChange(snapshot: DataSnapshot) {
//                var contractor = snapshot.getValue(Contractor::class.java)
//                if (contractor?.feedbackReceived != null) {
//                    for(fd in contractor.feedbackReceived){
//                        if(fd.feedbackId == uid){
//                            contractor.feedbackReceived.remove(fd)
//                            break
//                        }
//                    }
//                    reference.child(contractorId).setValue(contractor).addOnCompleteListener { task ->
//                        if(task.isSuccessful){
//                            Snackbar.make(singleFeedbackBinding.constraintLayout,"The feedback has been deleted",
//                                Snackbar.LENGTH_LONG).setAction("close",View.OnClickListener { }).show()
//                            requireActivity().supportFragmentManager.popBackStack()
//                        }
//                        else{
//                            Snackbar.make(singleFeedbackBinding.constraintLayout,"The deletion was not successful, Please try again!",Snackbar.LENGTH_LONG).setAction("close",View.OnClickListener { }).show()
//                        }
//                    }
//                }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
//            }
//        })
    }

    private fun receiveDetailsFromRecyclerView() {
        singleFeedbackBinding.textViewStdName.text = sharedViewModel.feedback.studentName
        singleFeedbackBinding.textViewStdFeedback.text = sharedViewModel.feedback.feedbackMessage
    }

}