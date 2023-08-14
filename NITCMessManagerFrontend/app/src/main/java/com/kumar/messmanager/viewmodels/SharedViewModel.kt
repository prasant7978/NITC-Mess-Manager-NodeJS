package com.kumar.messmanager.viewmodels

import androidx.lifecycle.ViewModel
import com.kumar.messmanager.model.Student

class SharedViewModel: ViewModel() {
    lateinit var student: Student
}