package com.kumar.messmanager.authentication

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.View
import android.widget.Toast
import com.kumar.messmanager.R
import com.kumar.messmanager.admin.AdminDashboardActivity
import com.kumar.messmanager.authentication.services.AuthenticationServiceBuilder
import com.kumar.messmanager.authentication.services.AuthenticationServices
import com.kumar.messmanager.contractor.ContractorDashboard
import com.kumar.messmanager.databinding.ActivityLoginBinding
import com.kumar.messmanager.student.StudentDashboardActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    lateinit var loginBinding : ActivityLoginBinding
    private var userType : String = "Student"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        val view = loginBinding.root

        setContentView(view)

        loginBinding.progressBarLogin.visibility = View.INVISIBLE

        loginBinding.adminLoginTypeButton.setOnClickListener {
            loginBinding.adminLoginTypeImage.setBackgroundResource(R.drawable.login_type_shape)
            loginBinding.adminLoginTypeButton.setTextColor(Color.WHITE)
            loginBinding.studentLoginTypeImage.setBackgroundResource(R.drawable.login_type_transparent_bg_shape)
            loginBinding.studentLoginTypeButton.setTextColor(Color.BLACK)
            loginBinding.contractorLoginTypeImage.setBackgroundResource(R.drawable.login_type_transparent_bg_shape)
            loginBinding.contractorLoginTypeButton.setTextColor(Color.BLACK)

            userType = "Admin"

            loginBinding.textViewSignup.visibility = View.INVISIBLE
        }

        loginBinding.studentLoginTypeButton.setOnClickListener {
            loginBinding.adminLoginTypeImage.setBackgroundResource(R.drawable.login_type_transparent_bg_shape)
            loginBinding.adminLoginTypeButton.setTextColor(Color.BLACK)
            loginBinding.studentLoginTypeImage.setBackgroundResource(R.drawable.login_type_shape)
            loginBinding.studentLoginTypeButton.setTextColor(Color.WHITE)
            loginBinding.contractorLoginTypeImage.setBackgroundResource(R.drawable.login_type_transparent_bg_shape)
            loginBinding.contractorLoginTypeButton.setTextColor(Color.BLACK)

            userType = "Student"

            loginBinding.textViewSignup.visibility = View.VISIBLE
        }

        loginBinding.contractorLoginTypeButton.setOnClickListener {
            loginBinding.adminLoginTypeImage.setBackgroundResource(R.drawable.login_type_transparent_bg_shape)
            loginBinding.adminLoginTypeButton.setTextColor(Color.BLACK)
            loginBinding.studentLoginTypeImage.setBackgroundResource(R.drawable.login_type_transparent_bg_shape)
            loginBinding.studentLoginTypeButton.setTextColor(Color.BLACK)
            loginBinding.contractorLoginTypeImage.setBackgroundResource(R.drawable.login_type_shape)
            loginBinding.contractorLoginTypeButton.setTextColor(Color.WHITE)

            userType = "Mess Contractor"

            loginBinding.textViewSignup.visibility = View.INVISIBLE
        }

        loginBinding.buttonSignin.setOnClickListener {
            val userEmail = loginBinding.editTextLoginEmail.text.toString()
            val userPassword = loginBinding.editTextLoginPassword.text.toString()
            if(userEmail.isEmpty() || userPassword.isEmpty()){
                Toast.makeText(applicationContext, "Please enter both email and password to login.", Toast.LENGTH_SHORT).show()
            }
            else {
                signinWithFirebase(userEmail, userPassword, userType)
            }
        }

        loginBinding.textViewSignup.setOnClickListener {
            val intent = Intent(this@LoginActivity, SignUpActivity::class.java)
            startActivity(intent)
        }

        loginBinding.textViewForgotPassword.setOnClickListener {
            val intent = Intent(this@LoginActivity, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }
    }

//    override fun onStart() {
//        super.onStart()
//
//        val user = auth.currentUser
//        if(user != null){
//            val uid = user.uid
//
//            studentReference.orderByChild("studentId").equalTo(uid)
//                .addListenerForSingleValueEvent(object : ValueEventListener{
//                    override fun onDataChange(snapshot: DataSnapshot) {
//                        if(snapshot.exists()){
////                            Toast.makeText(applicationContext,"Welcome",Toast.LENGTH_SHORT).show()
//                            if(auth.currentUser?.isEmailVerified == true) {
//                                val intent =
//                                    Intent(this@LoginActivity, StudentDashboardActivity::class.java)
//                                startActivity(intent)
//                                finish()
//                            }
//                        }
//                    }
//
//                    override fun onCancelled(error: DatabaseError) {
//                        TODO("Not yet implemented")
//                    }
//                })
//
//            adminReference.orderByChild("adminId").equalTo(uid)
//                .addListenerForSingleValueEvent(object : ValueEventListener{
//                    override fun onDataChange(snapshot: DataSnapshot) {
//                        if(snapshot.exists()){
////                            Toast.makeText(applicationContext,"Welcome",Toast.LENGTH_SHORT).show()
//                            val intent = Intent(this@LoginActivity,AdminDashboardActivity::class.java)
//                            startActivity(intent)
//                            finish()
//                        }
//                    }
//
//                    override fun onCancelled(error: DatabaseError) {
//                        TODO("Not yet implemented")
//                    }
//                })
//
//            contractorReference.orderByChild("contractorId").equalTo(uid)
//                .addListenerForSingleValueEvent(object : ValueEventListener{
//                    override fun onDataChange(snapshot: DataSnapshot) {
//                        if(snapshot.exists()){
////                            Toast.makeText(applicationContext,"Welcome",Toast.LENGTH_SHORT).show()
//                            val intent = Intent(this@LoginActivity,ContractorDashboard::class.java)
//                            startActivity(intent)
//                            finish()
//                        }
//                    }
//
//                    override fun onCancelled(error: DatabaseError) {
//                        TODO("Not yet implemented")
//                    }
//                })
//        }
//    }

    private fun signinWithFirebase(email:String, pass:String, userType:String){
        loginBinding.buttonSignin.isClickable = false
        loginBinding.progressBarLogin.visibility = View.VISIBLE

        when (userType) {
            "Student" -> {
                if (!checkConstraints(email)) {
                    Toast.makeText(this@LoginActivity, "Enter a valid nitc email id", Toast.LENGTH_SHORT).show()
                    loginBinding.buttonSignin.isClickable = true
                    loginBinding.progressBarLogin.visibility = View.INVISIBLE
                }
                else {
                    val map: HashMap<String, String> = HashMap()
                    map["studentEmail"] = email
                    map["studentPassword"] = pass
                    map["userType"] = userType

                    val loginService: AuthenticationServices = AuthenticationServiceBuilder.buildService(AuthenticationServices::class.java)
                    val requestCall = loginService.loginStudent(map)

                    requestCall.enqueue(object: Callback<Boolean>{
                        override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                            if(response.code() == 400){
                                Toast.makeText(applicationContext, "Incorrect username or password", Toast.LENGTH_SHORT).show()
                                loginBinding.buttonSignin.isClickable = true
                                loginBinding.progressBarLogin.visibility = View.INVISIBLE
                            }
                            else if(response.code() == 200){
                                val sharedPreferences = this@LoginActivity.getSharedPreferences("saveToken", Context.MODE_PRIVATE)
                                val editor = sharedPreferences.edit()
                                editor.putString("token",response.headers()["user-auth-token"].toString())
                                editor.apply()

                                val intent = Intent(this@LoginActivity, StudentDashboardActivity::class.java)
                                intent.putExtra("userType", userType)
                                startActivity(intent)

                                loginBinding.buttonSignin.isClickable = true
                                loginBinding.progressBarLogin.visibility = View.INVISIBLE

                                finish()
                            }
                        }

                        override fun onFailure(call: Call<Boolean>, t: Throwable) {
                            Toast.makeText(applicationContext, t.localizedMessage?.toString(), Toast.LENGTH_SHORT).show()
                            loginBinding.buttonSignin.isClickable = true
                            loginBinding.progressBarLogin.visibility = View.INVISIBLE
                        }

                    })
                }
            }

            "Admin" -> {
                val map: HashMap<String, String> = HashMap()
                map["adminEmail"] = email
                map["adminPassword"] = pass
                map["userType"] = userType

                val loginService: AuthenticationServices = AuthenticationServiceBuilder.buildService(AuthenticationServices::class.java)
                val requestCall = loginService.loginAdmin(map)

                requestCall.enqueue(object: Callback<Boolean>{
                    override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                        if(response.isSuccessful){
                            val sharedPreferences = this@LoginActivity.getSharedPreferences("saveToken", Context.MODE_PRIVATE)
                            val editor = sharedPreferences.edit()
                            editor.putString("token", response.headers()["user-auth-token"].toString())
                            editor.apply()

                            val intent = Intent(this@LoginActivity, AdminDashboardActivity::class.java)
                            startActivity(intent)

                            loginBinding.buttonSignin.isClickable = true
                            loginBinding.progressBarLogin.visibility = View.INVISIBLE

                            finish()
                        }
                        else{
                            Toast.makeText(applicationContext, "Incorrect username or password", Toast.LENGTH_SHORT).show()
                            loginBinding.buttonSignin.isClickable = true
                            loginBinding.progressBarLogin.visibility = View.INVISIBLE
                        }
                    }

                    override fun onFailure(call: Call<Boolean>, t: Throwable) {
                        Toast.makeText(applicationContext, t.localizedMessage?.toString(), Toast.LENGTH_SHORT).show()
                        loginBinding.buttonSignin.isClickable = true
                        loginBinding.progressBarLogin.visibility = View.INVISIBLE
                    }

                })
            }

            "Mess Contractor" -> {
                val map: HashMap<String, String> = HashMap()
                map["contractorEmail"] = email
                map["contractorPassword"] = pass
                map["userType"] = "Contractor"

                val loginService: AuthenticationServices = AuthenticationServiceBuilder.buildService(AuthenticationServices::class.java)
                val requestCall = loginService.loginContractor(map)

                requestCall.enqueue(object: Callback<Boolean>{
                    override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                        if(response.isSuccessful){
                            val sharedPreferences = this@LoginActivity.getSharedPreferences("saveToken", Context.MODE_PRIVATE)
                            val editor = sharedPreferences.edit()
                            editor.putString("token", response.headers()["user-auth-token"].toString())
                            editor.apply()

                            val intent = Intent(this@LoginActivity, ContractorDashboard::class.java)
                            intent.putExtra("userType",userType)
                            startActivity(intent)

                            loginBinding.buttonSignin.isClickable = true
                            loginBinding.progressBarLogin.visibility = View.INVISIBLE

                            finish()
                        }
                        else{
                            Toast.makeText(applicationContext, "Incorrect username or password", Toast.LENGTH_SHORT).show()
                            loginBinding.buttonSignin.isClickable = true
                            loginBinding.progressBarLogin.visibility = View.INVISIBLE
                        }
                    }

                    override fun onFailure(call: Call<Boolean>, t: Throwable) {
                        Toast.makeText(applicationContext, t.localizedMessage?.toString(), Toast.LENGTH_SHORT).show()
                        loginBinding.buttonSignin.isClickable = true
                        loginBinding.progressBarLogin.visibility = View.INVISIBLE
                    }

                })
            }

            "" -> {
                loginBinding.buttonSignin.isClickable = true
                loginBinding.progressBarLogin.visibility = View.INVISIBLE
                Toast.makeText(applicationContext, "Select a user type", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkConstraints(email: String): Boolean {
        if(email.contains('_')) {
            val roll = email.substring(email.indexOf("_") + 1, email.length)
            if (roll[0] == 'm' || roll[0] == 'b' || roll[0] == 'p') {
                if(roll.contains('@')) {
                    val domain = roll.substring(roll.indexOf("@") + 1, roll.length)
                    return domain == "nitc.ac.in"
                }
                else{
                    return false
                }
            } else {
                return false
            }
        }
        else{
            return false
        }
    }

}