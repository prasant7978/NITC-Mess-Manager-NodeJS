package com.kumar.messmanager.student

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
import com.kumar.messmanager.databinding.FragmentStudentFeedbackBinding
import com.kumar.messmanager.services.FeedbackServices
import com.kumar.messmanager.services.ProfileService
import com.kumar.messmanager.services.ServiceBuilder
import com.kumar.messmanager.viewmodels.SharedViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StudentFeedbackFragment : Fragment() {

    private lateinit var studentFeedbackBinding: FragmentStudentFeedbackBinding
    val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        studentFeedbackBinding = FragmentStudentFeedbackBinding.inflate(inflater,container,false)

        studentFeedbackBinding.buttonSend.setOnClickListener {
            studentFeedbackBinding.buttonSend.isClickable = false
            val msg = studentFeedbackBinding.feedbackMessage.text.toString()
            if(msg.isEmpty())
                Toast.makeText(activity,"Please write any feedback to submit", Toast.LENGTH_SHORT).show()
            else {
                sendFeedback(msg)
            }
        }

        return studentFeedbackBinding.root
    }

    private fun sendFeedback(msg : String){
        val studentName = sharedViewModel.student.studentName
        val messName = sharedViewModel.student.messEnrolled
        val token = sharedViewModel.token

        val map: HashMap<String, String> = HashMap()
        map["studentName"] = studentName
        map["feedbackMessage"] = msg

        val feedbackService: FeedbackServices = ServiceBuilder.buildService(FeedbackServices::class.java)
        val requestCall = feedbackService.addFeedback(map, token, messName)

        requestCall.enqueue(object: Callback<Boolean> {
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if(response.isSuccessful){
                    Snackbar.make(studentFeedbackBinding.constraintLayoutFeedback,"We're grateful for your feedback and will incorporate it into our efforts.",
                        Snackbar.LENGTH_INDEFINITE).setAction("Close", View.OnClickListener { }).show()

                    val fragmentManager : FragmentManager = requireActivity().supportFragmentManager
                    val fragmentTransaction : FragmentTransaction = fragmentManager.beginTransaction()
                    val studentDashboardFragment = StudentDashboardFragment()

                    fragmentTransaction.replace(R.id.frameLayout,studentDashboardFragment)
                    fragmentTransaction.addToBackStack(null)
                    fragmentTransaction.commit()
                }
                else
                    Toast.makeText(context, "Server Error", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Log.d("failure", t.localizedMessage.toString())
                Toast.makeText(context, t.localizedMessage, Toast.LENGTH_SHORT).show()
            }

        })

        studentFeedbackBinding.feedbackMessage.setText("")
        studentFeedbackBinding.buttonSend.isClickable = true
    }

}