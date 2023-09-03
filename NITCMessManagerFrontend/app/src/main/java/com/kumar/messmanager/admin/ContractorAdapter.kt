package com.kumar.messmanager.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.kumar.messmanager.databinding.ItemContractorBinding
import com.kumar.messmanager.R
import com.kumar.messmanager.admin.UpdateContractorFragment
import com.kumar.messmanager.model.Contractor

class ContractorAdapter(private var contractorList : ArrayList<Contractor>, fragment: Fragment) : RecyclerView.Adapter<ContractorAdapter.ContractorViewHolder>(){
//    private val adminViewModel = ViewModelProvider(fragment.requireActivity())[AdminViewModel::class.java]
    class ContractorViewHolder(val adapterBinding : ItemContractorBinding) : RecyclerView.ViewHolder(adapterBinding.root){
        val name : TextView = adapterBinding.contractorName
        val mess : TextView = adapterBinding.messName
        val type : TextView = adapterBinding.foodType
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContractorViewHolder {
        val binding = ItemContractorBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ContractorViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return contractorList.size
    }

    override fun onBindViewHolder(holder: ContractorViewHolder, position: Int) {
        holder.adapterBinding.contractorName.text = contractorList[position].contractorName
        holder.adapterBinding.messName.text = contractorList[position].messName
        holder.adapterBinding.foodType.text = contractorList[position].foodType

        holder.adapterBinding.constraintLayout.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("contractorId", contractorList[position]._id)
//            bundle.putString("contractorEmail",contractorList[position].contractorEmail)
//            bundle.putString("contractorPassword",contractorList[position].contractorPassword)
//            bundle.putString("contractorName",contractorList[position].contractorName)
//            bundle.putString("costPerDay",contractorList[position].costPerDay.toString())
//            bundle.putString("messName",contractorList[position].messName)
//            bundle.putString("foodType",contractorList[position].foodType)
//            bundle.putString("capacity",contractorList[position].capacity.toString())
//            bundle.putString("availability",contractorList[position].availability.toString())
//            adminViewModel.feedbackList = contractorList[position].feedbackReceived
//            adminViewModel.studentEnrolledList = contractorList[position].studentEnrolled

            val updateContractorFragment = UpdateContractorFragment()
            updateContractorFragment.arguments = bundle

            val appCompatActivity = it.context as AppCompatActivity
            appCompatActivity.supportFragmentManager.beginTransaction()
                .replace(R.id.frameLayout,updateContractorFragment)
                .addToBackStack(null)
                .commit()
        }
    }

    fun searchByFoodType(searchList : ArrayList<Contractor>){
        contractorList = searchList
        notifyDataSetChanged()
    }

}