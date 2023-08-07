package com.kumar.messmanager.authentication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.kumar.messmanager.authentication.services.AuthenticationServiceBuilder
import com.kumar.messmanager.authentication.services.AuthenticationServices
import com.kumar.messmanager.databinding.ActivitySignUpBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : AppCompatActivity() {
    private lateinit var signupBinding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        signupBinding = ActivitySignUpBinding.inflate(layoutInflater)
        val view = signupBinding.root

        setContentView(view)

        supportActionBar?.title = "Sign Up"

        signupBinding.progressBarSignup.visibility = View.INVISIBLE

        signupBinding.buttonSignup.setOnClickListener {
            val name = signupBinding.editTextSignupName.text.toString()
            val roll = signupBinding.editTextSignupRollno.text.toString().uppercase()
            val email = signupBinding.editTextSignupEmail.text.toString()
            val password = signupBinding.editTextSignupPassword.text.toString()

            if(name.isEmpty() || email.isEmpty() ||password.isEmpty() ||roll.isEmpty()){
                Toast.makeText(this,"Please provide complete information",Toast.LENGTH_SHORT).show()
            }
            else {
                if(!checkConstraints(email)){
                    Toast.makeText(this@SignUpActivity,"Enter a valid nitc email id",Toast.LENGTH_SHORT).show()
                }
                else {
                    signupWithFirebase(email, password, name, roll)
                }
            }
        }
    }

    private fun signupWithFirebase(email:String, password:String, name:String, roll:String){
        signupBinding.buttonSignup.isClickable = false
        signupBinding.progressBarSignup.visibility = View.VISIBLE

        val map: HashMap<String, String> = HashMap()
        map["studentEmail"] = email
        map["studentPassword"] = password
        map["studentName"] = name
        map["studentRollNo"] = roll
        map["userType"] = "Student"

        val signupService: AuthenticationServices = AuthenticationServiceBuilder.buildService(AuthenticationServices::class.java)
        val requestCall = signupService.singnupStudent(map)

        requestCall.enqueue(object: Callback<Boolean>{
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if(response.isSuccessful){
                    Toast.makeText(this@SignUpActivity,"Success! Your account has been created.",Toast.LENGTH_LONG).show()
                    finish()
                }
                else if(response.code() == 404){
                    Toast.makeText(this@SignUpActivity,"Email address already in use. Please choose another one.",Toast.LENGTH_LONG).show()
                }
                else{
                    Toast.makeText(this@SignUpActivity,"Oops! Something went wrong. Please contact support.",Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Toast.makeText(applicationContext, t.localizedMessage?.toString(), Toast.LENGTH_SHORT).show()
            }

        })

        signupBinding.buttonSignup.isClickable = true
        signupBinding.progressBarSignup.visibility = View.INVISIBLE
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