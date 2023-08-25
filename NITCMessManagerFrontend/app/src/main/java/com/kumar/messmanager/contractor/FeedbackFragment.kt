package com.kumar.messmanager.contractor

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.kumar.messmanager.databinding.FragmentFeedbackBinding
import com.kumar.messmanager.model.Contractor
import com.kumar.messmanager.model.Feedback
import com.kumar.messmanager.services.FeedbackServices
import com.kumar.messmanager.services.ServiceBuilder
import com.kumar.messmanager.viewmodels.SharedViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FeedbackFragment : Fragment() {

    private lateinit var feedbackFragmentBinding : FragmentFeedbackBinding
    val sharedViewModel: SharedViewModel by activityViewModels()
    var feedbackList = ArrayList<Feedback>()
    lateinit var feedbackAdapter: FeedbackAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        feedbackFragmentBinding = FragmentFeedbackBinding.inflate(inflater,container,false)

        feedbackFragmentBinding.textViewNoFeedbackToShow.visibility = View.INVISIBLE

        retrieveFeedbackListFromDb()

        return feedbackFragmentBinding.root
    }

    private fun retrieveFeedbackListFromDb() {
        feedbackFragmentBinding.progressBar.visibility = View.VISIBLE

        val token = sharedViewModel.token
        val feedbackServices: FeedbackServices =
            ServiceBuilder.buildService(FeedbackServices::class.java)
        val requestCall = feedbackServices.getAllFeedbacks(token)

        requestCall.enqueue(object : Callback<ArrayList<Feedback>> {
            override fun onResponse(
                call: Call<ArrayList<Feedback>>,
                response: Response<ArrayList<Feedback>>
            ) {
                if (response.isSuccessful) {
                    val feedbacks = response.body()
                    if (!feedbacks.isNullOrEmpty()) {
                        feedbackList = feedbacks

                        feedbackList.reverse()
                        feedbackFragmentBinding.recyclerView.layoutManager = LinearLayoutManager(activity)
                        feedbackAdapter = FeedbackAdapter(feedbackList, sharedViewModel)
                        feedbackFragmentBinding.recyclerView.adapter = feedbackAdapter
                    } else {
                        feedbackFragmentBinding.recyclerView.visibility = View.INVISIBLE
                        feedbackFragmentBinding.textViewNoFeedbackToShow.visibility = View.VISIBLE
                    }
                } else
                    Toast.makeText(context, "Server Error", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<ArrayList<Feedback>>, t: Throwable) {
                Log.d("failure", t.localizedMessage.toString())
                Toast.makeText(context, t.localizedMessage, Toast.LENGTH_SHORT).show()
            }

        })

        feedbackFragmentBinding.progressBar.visibility = View.INVISIBLE
    }

}