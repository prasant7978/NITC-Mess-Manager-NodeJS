package com.kumar.messmanager.admin

import androidx.lifecycle.ViewModel
import com.kumar.messmanager.model.Feedback
import com.kumar.messmanager.model.Student

class AdminViewModel: ViewModel() {

    lateinit var feedbackList:ArrayList<Feedback>
    lateinit var studentEnrolledList:ArrayList<Student>

}