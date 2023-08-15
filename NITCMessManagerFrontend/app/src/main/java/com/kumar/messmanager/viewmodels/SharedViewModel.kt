package com.kumar.messmanager.viewmodels

import androidx.lifecycle.ViewModel
import com.kumar.messmanager.model.Admin
import com.kumar.messmanager.model.Contractor
import com.kumar.messmanager.model.Student

class SharedViewModel: ViewModel() {
    lateinit var student: Student
    lateinit var contractor: Contractor
    lateinit var admin: Admin
    lateinit var userType: String
    lateinit var token: String
}