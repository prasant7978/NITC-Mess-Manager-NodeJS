package com.kumar.messmanager.model

import com.kumar.messmanager.model.Feedback
import com.kumar.messmanager.model.Menu
import com.kumar.messmanager.model.Student

data class Contractor(var _id : String = "",
                      var contractorEmail : String = "",
                      var contractorPassword : String = "",
                      var userType : String = "",
                      var contractorName : String = "",
                      var costPerDay : Int = 0,
                      var messName : String = "",
                      var foodType : String = "",
                      var capacity : Int = 0,
                      var availability : Int = 0,
                      var totalDue : Int = 0,
                      var totalEnrolled : Int = 0,
                      var messMenu : ArrayList<Menu> = ArrayList(),
                      var feedbackReceived : ArrayList<Feedback> = ArrayList(),
                      var studentEnrolled : ArrayList<String> = ArrayList()
) {
}