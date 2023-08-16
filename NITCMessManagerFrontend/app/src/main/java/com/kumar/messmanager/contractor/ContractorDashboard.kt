package com.kumar.messmanager.contractor

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.kumar.messmanager.R
import com.kumar.messmanager.authentication.LoginActivity
import com.kumar.messmanager.databinding.ActivityContractorDashboardBinding

class ContractorDashboard : AppCompatActivity() {

    private lateinit var contractorDashboardBinding: ActivityContractorDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        contractorDashboardBinding = ActivityContractorDashboardBinding.inflate(layoutInflater)
        val view = contractorDashboardBinding.root

        setContentView(view)

        val fragmentManager : FragmentManager = supportFragmentManager
        val fragmentTransaction : FragmentTransaction = fragmentManager.beginTransaction()
        val contractorDashboardFragment = ContractorDashboardFragment()

        fragmentTransaction.add(R.id.frameLayout,contractorDashboardFragment)
        fragmentTransaction.commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_signout,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.signOut){
            val dialog = AlertDialog.Builder(this)
            dialog.setTitle("Sign Out")
            dialog.setCancelable(false)
            dialog.setMessage("Are you sure, want to sign out ?")
            dialog.setNegativeButton("No", DialogInterface.OnClickListener { dialog, which ->
                dialog.cancel()
            })
            dialog.setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
                val sharedPreferences = this@ContractorDashboard.getSharedPreferences("saveToken", MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.remove("token")
                editor.remove("userType")
                editor.apply()

                Toast.makeText(this,"Sign out is successfull", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@ContractorDashboard, LoginActivity::class.java)
                startActivity(intent)
                finish()
            })
            dialog.create().show()
        }
        return true
    }
}