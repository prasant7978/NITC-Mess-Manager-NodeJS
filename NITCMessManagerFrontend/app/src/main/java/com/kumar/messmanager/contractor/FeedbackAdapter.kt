package com.kumar.messmanager.contractor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.kumar.messmanager.R
import com.kumar.messmanager.databinding.ItemFeedbackBinding
import com.kumar.messmanager.model.Feedback
import com.kumar.messmanager.viewmodels.SharedViewModel

class FeedbackAdapter(private var feedbackList : ArrayList<Feedback>, var sharedViewModel: SharedViewModel) : RecyclerView.Adapter<FeedbackAdapter.FeedbackViewHolder>(){
    class FeedbackViewHolder(val adapterBinding : ItemFeedbackBinding) : RecyclerView.ViewHolder(adapterBinding.root){
        val name : TextView = adapterBinding.textViewFeedbackStudentName
        val msg : TextView = adapterBinding.textViewFeedbackMessage
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedbackViewHolder {
        val binding = ItemFeedbackBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return FeedbackViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return feedbackList.size
    }

    override fun onBindViewHolder(holder: FeedbackViewHolder, position: Int) {
        holder.adapterBinding.textViewFeedbackStudentName.text = feedbackList[position].studentName
        holder.adapterBinding.textViewFeedbackMessage.text = feedbackList[position].feedbackMessage
        holder.adapterBinding.constraintLayout.setOnClickListener {
            sharedViewModel.feedback = feedbackList[position]

            val singleFeedbackFragment = SingleFeedbackFragment()

            val appCompatActivity = it.context as AppCompatActivity
            appCompatActivity.supportFragmentManager.beginTransaction()
                .replace(R.id.frameLayout,singleFeedbackFragment)
                .addToBackStack(null)
                .commit()
        }
    }
}