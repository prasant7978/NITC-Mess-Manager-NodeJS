package com.kumar.messmanager.contractor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.kumar.messmanager.databinding.FragmentFeedbackBinding
import com.kumar.messmanager.model.Contractor
import com.kumar.messmanager.model.Feedback

class FeedbackFragment : Fragment() {

    lateinit var feedbackFragmentBinding : FragmentFeedbackBinding
    var feedbackList = ArrayList<Feedback>()
//    val db : FirebaseDatabase = FirebaseDatabase.getInstance()
//    val ref = db.reference.child("contractors")
    lateinit var feedbackAdapter: FeedbackAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        feedbackFragmentBinding = FragmentFeedbackBinding.inflate(inflater,container,false)

        feedbackFragmentBinding.textViewNoFeedbackToShow.visibility = View.INVISIBLE

//        retrieveFeedbackListFromDb()

        return feedbackFragmentBinding.root
    }

//    private fun retrieveFeedbackListFromDb() {
//        feedbackFragmentBinding.progressBar.visibility = View.VISIBLE
//        val uid = FirebaseAuth.getInstance().currentUser?.uid
//        ref.orderByChild("contractorId").equalTo(uid).addListenerForSingleValueEvent(object : ValueEventListener{
//            override fun onDataChange(snapshot: DataSnapshot) {
//                feedbackList.clear()
//                for(fd in snapshot.children){
//                    val contractor = fd.getValue(Contractor::class.java)
//                    val feedback = contractor?.feedbackReceived
//                    if(feedback?.isNotEmpty() == true)
//                        feedbackList = feedback
//                }
//
//                if(feedbackList.isEmpty()){
//                    feedbackFragmentBinding.recyclerView.visibility = View.INVISIBLE
//                    feedbackFragmentBinding.textViewNoFeedbackToShow.visibility = View.VISIBLE
//                }
//                else {
//                    feedbackList.reverse()
//                    feedbackFragmentBinding.recyclerView.layoutManager =
//                        LinearLayoutManager(activity)
//                    feedbackAdapter = FeedbackAdapter(feedbackList)
//                    feedbackFragmentBinding.recyclerView.adapter = feedbackAdapter
//                }
//
//                feedbackFragmentBinding.progressBar.visibility = View.INVISIBLE
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
//            }
//
//        })
//    }

}