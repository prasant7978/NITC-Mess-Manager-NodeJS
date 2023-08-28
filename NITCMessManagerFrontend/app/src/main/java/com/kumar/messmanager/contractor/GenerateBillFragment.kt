package com.kumar.messmanager.contractor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import com.kumar.messmanager.R
import com.kumar.messmanager.databinding.FragmentGenerateBillBinding
import com.kumar.messmanager.model.Contractor
import com.kumar.messmanager.viewmodels.SharedViewModel

class GenerateBillFragment : Fragment() {

    private lateinit var generateBillBinding: FragmentGenerateBillBinding
    val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        generateBillBinding = FragmentGenerateBillBinding.inflate(inflater,container,false)

        val contractor = sharedViewModel.contractor

        if(contractor.totalDue == 0){
            val fragmentManager : FragmentManager = parentFragmentManager
            val fragmentTransaction : FragmentTransaction = fragmentManager.beginTransaction()
            val calculateBillPerStudentFragment = CalculateBillPerStudentFragment(childFragmentManager)

            fragmentTransaction.replace(R.id.frameLayoutBill,calculateBillPerStudentFragment)
            fragmentTransaction.commit()
        }
        else{
            val fragmentManager : FragmentManager = parentFragmentManager
            val fragmentTransaction : FragmentTransaction = fragmentManager.beginTransaction()
            val totalBillGeneratedFragment = TotalBillGeneratedFragment()

            fragmentTransaction.replace(R.id.frameLayoutBill,totalBillGeneratedFragment)
            fragmentTransaction.commit()
        }

        return generateBillBinding.root
    }

}