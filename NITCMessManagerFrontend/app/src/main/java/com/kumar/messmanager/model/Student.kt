package com.kumar.messmanager.model

data class Student(var _id : String = "",
                   var studentName : String = "",
                   var studentEmail : String = "",
                   var studentPassword : String = "",
                   var studentRollNo : String = "",
                   var userType : String = "",
                   var messBill : Int = 0,
                   var paymentStatus : String = "",
                   var messEnrolled : String = "") {
}