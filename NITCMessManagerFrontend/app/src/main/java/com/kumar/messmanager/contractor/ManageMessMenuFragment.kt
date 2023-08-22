package com.kumar.messmanager.contractor

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.kumar.messmanager.GetMenuAccess
import com.kumar.messmanager.contractor.access.GetProfileAccess
import com.kumar.messmanager.databinding.FragmentManageMessMenuBinding
import com.kumar.messmanager.model.Contractor
import com.kumar.messmanager.model.Menu
import com.kumar.messmanager.model.Student
import com.kumar.messmanager.services.MessMenuServices
import com.kumar.messmanager.services.ServiceBuilder
import com.kumar.messmanager.viewmodels.SharedViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ManageMessMenuFragment : Fragment() {

    private lateinit var manageMessMenuBinding: FragmentManageMessMenuBinding
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var contractor: Contractor
    private lateinit var student: Student
    private var days  : Array<String> = arrayOf("Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday")
    private var previousSelectedIndex = 0
    var menu: Menu? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        manageMessMenuBinding = FragmentManageMessMenuBinding.inflate(inflater,container,false)

        val userType = sharedViewModel.userType
        var messName = ""

        if(userType == "Student"){
            manageMessMenuBinding.textInputBreakfastDetails.isClickable = false
            manageMessMenuBinding.textInputBreakfastDetails.isEnabled = false
            manageMessMenuBinding.textInputLunchDetails.isClickable = false
            manageMessMenuBinding.textInputLunchDetails.isEnabled = false
            manageMessMenuBinding.textInputDinnerDetails.isClickable = false
            manageMessMenuBinding.textInputDinnerDetails.isEnabled = false
            manageMessMenuBinding.buttonUpdateMenu.visibility = View.GONE

            student = sharedViewModel.student
            messName = student.messEnrolled

//            fetchMessMenuForStudent(messName, days[previousSelectedIndex])
        }
        else {
            contractor = sharedViewModel.contractor
            messName = contractor.messName
            fetchMessMenu(messName, days[previousSelectedIndex])
        }

        manageMessMenuBinding.buttonSelectDay.setOnClickListener {
            val dayBuilder = AlertDialog.Builder(context)
            dayBuilder.setTitle("Select day")
                .setSingleChoiceItems(days,previousSelectedIndex) { dialog, selectedIndex ->
                    val selectedDay = days[selectedIndex]
                    previousSelectedIndex = selectedIndex
//                    manageMessMenuBinding.buttonSelectDay.text = selectedDay
                    manageMessMenuBinding.textViewSelectedDay.text = selectedDay
                    if(userType == "Student"){
//                        fetchMessMenuForStudent(messName, days[previousSelectedIndex])
                    }
                    else
                        fetchMessMenu(messName, selectedDay)

                    dialog.dismiss()
                }
                .setPositiveButton("Ok"){dialog, which->
                    val selectedDay = days[0]
//                    manageMessMenuBinding.buttonSelectDay.text = selectedDay
                    manageMessMenuBinding.textViewSelectedDay.text = selectedDay
                    if(userType == "Student"){
//                        fetchMessMenuForStudent(messName, days[previousSelectedIndex])
                    }
                    else
                        fetchMessMenu(messName, selectedDay)

                    dialog.dismiss()
                }
                .create().show()
        }

        manageMessMenuBinding.buttonUpdateMenu.setOnClickListener {
            val breakfast = manageMessMenuBinding.textInputBreakfastDetails.text.toString()
            val lunch = manageMessMenuBinding.textInputLunchDetails.text.toString()
            val dinner = manageMessMenuBinding.textInputDinnerDetails.text.toString()
            Log.d("menu","update clicked")
            if(breakfast.isEmpty() || lunch.isEmpty() || dinner.isEmpty()){
                Toast.makeText(activity,"Please enter all menu details",Toast.LENGTH_SHORT).show()
            }
            else{
                Log.d("menu","Show dialog")
                val dialog = AlertDialog.Builder(activity)
                dialog.setTitle("Update Mess Menu")
                dialog.setCancelable(false)
                dialog.setMessage("Are you sure, want to update mess menu for ${days[previousSelectedIndex]} ?")
                dialog.setNegativeButton("No", DialogInterface.OnClickListener { dialog, which ->
                    dialog.cancel()
                })
                dialog.setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
                    Log.d("menu","update called")
                    updateMessMenu(breakfast,lunch,dinner,days[previousSelectedIndex],previousSelectedIndex, messName)
                })
                dialog.create().show()
            }
        }

        return manageMessMenuBinding.root
    }

    private fun updateMessMenu(breakfast: String, lunch: String, dinner: String, selectedDay: String, dayIndex:Int, messName: String) {
        val map: HashMap<String, String> = HashMap()
        if(menu != null)
            map["_id"] = menu!!._id.toString()
        map["day"] = selectedDay
        map["breakfast"] = breakfast
        map["lunch"] = lunch
        map["dinner"] = dinner

        val token = sharedViewModel.token
        val messMenuServices: MessMenuServices = ServiceBuilder.buildService(MessMenuServices::class.java)
        val requestCall = messMenuServices.updateMenu(map, token)

        requestCall.enqueue(object: Callback<Boolean>{
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if(response.isSuccessful){
                    fetchMessMenu(messName, selectedDay)
                }
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })

//        var flag = false
//        val menuList = contractor.messMenu
//        for(menu in menuList){
//            if(menu.day == selectedDay){
//                flag = true
//                menu.breakfast = breakfast
//                menu.lunch = lunch
//                menu.dinner = dinner
//                break
//            }
//        }
//
//        if(!flag)
//                reference.child(uid).child("messMenu").child("$dayIndex").setValue(Menu(selectedDay,breakfast,lunch,dinner))
//        else {
//            contractor.messMenu = menuList
////                reference.child(contractor.contractorId).setValue(contractor)
//        }

        //        fetchMessMenu(uid,selectedDay)

//        reference.child(uid).addListenerForSingleValueEvent(object : ValueEventListener{
//            override fun onDataChange(snapshot: DataSnapshot) {
//                val contractor = snapshot.getValue(Contractor::class.java)
//                if(contractor != null){
////                    Log.d("menu","contractor found")
//                    var flag = false
//
//                    val menuList = contractor.messMenu
//
//                    for(menu in menuList){
//                        if(menu.day == selectedDay){
//                            flag = true
//                            menu.breakfast = breakfast
//                            menu.lunch = lunch
//                            menu.dinner = dinner
////                            Log.d("menu", menu.day)
////                            Log.d("menu", menu.breakfast)
////                            Log.d("menu", menu.lunch)
////                            Log.d("menu", menu.dinner)
//                            break
//                        }
//                    }
//
//                    if(!flag)
//                        reference.child(uid).child("messMenu").child("$dayIndex").setValue(Menu(selectedDay,breakfast,lunch,dinner))
//                    else {
//                        contractor.messMenu = menuList
//                        reference.child(contractor.contractorId).setValue(contractor)
//                    }
//
//                }
////                Log.d("menu",uid)
////                Log.d("menu",selectedDay)
//                fetchMessMenu(uid,selectedDay)
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
//            }
//
//        })
    }

    private fun fetchMessMenu(messName: String, selectedDay: String) {
        manageMessMenuBinding.textInputBreakfastDetails.setText("")
        manageMessMenuBinding.textInputLunchDetails.setText("")
        manageMessMenuBinding.textInputDinnerDetails.setText("")

        val getMenuAccess = GetMenuAccess(this)
        val getMenuCoroutineScope = CoroutineScope(Dispatchers.Main)

        getMenuCoroutineScope.launch {
            menu = getMenuAccess.retrieveMessMenu(messName, sharedViewModel, selectedDay)
            if(menu != null) {
                manageMessMenuBinding.textInputBreakfastDetails.setText(menu!!.breakfast)
                manageMessMenuBinding.textInputLunchDetails.setText(menu!!.lunch)
                manageMessMenuBinding.textInputDinnerDetails.setText(menu!!.dinner)
            }
            else{
                manageMessMenuBinding.textInputBreakfastDetails.setText("")
                manageMessMenuBinding.textInputLunchDetails.setText("")
                manageMessMenuBinding.textInputDinnerDetails.setText("")
//                Toast.makeText(activity,"No menu have been added for $selectedDay", Toast.LENGTH_SHORT).show()
            }
            getMenuCoroutineScope.cancel()
        }
    }

//    private fun fetchMessMenuForStudent(messName : String, selectedDay: String) {
//        manageMessMenuBinding.textInputBreakfastDetails.setText("")
//        manageMessMenuBinding.textInputLunchDetails.setText("")
//        manageMessMenuBinding.textInputDinnerDetails.setText("")
//
//        reference.orderByChild("messName").equalTo(messName).addListenerForSingleValueEvent(object : ValueEventListener{
//            override fun onDataChange(snapshot: DataSnapshot) {
//                for(ds in snapshot.children){
//                    val contractor = ds.getValue(Contractor::class.java)
//                    if(contractor != null) {
//                        val menuList = contractor.messMenu
//                        if (menuList.isNotEmpty()) {
//                            for (menu in menuList) {
//                                if (menu.day == selectedDay) {
//                                    manageMessMenuBinding.textInputBreakfastDetails.setText(menu.breakfast)
//                                    manageMessMenuBinding.textInputLunchDetails.setText(menu.lunch)
//                                    manageMessMenuBinding.textInputDinnerDetails.setText(menu.dinner)
//                                    break
//                                }
//                            }
//                        } else {
//                            manageMessMenuBinding.textInputBreakfastDetails.setText("")
//                            manageMessMenuBinding.textInputLunchDetails.setText("")
//                            manageMessMenuBinding.textInputDinnerDetails.setText("")
//                            Toast.makeText(
//                                activity,
//                                "No menu have been added for $selectedDay",
//                                Toast.LENGTH_SHORT
//                            ).show()
//                        }
//                    }
//
//                }
//
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
//            }
//
//        })
//    }

}