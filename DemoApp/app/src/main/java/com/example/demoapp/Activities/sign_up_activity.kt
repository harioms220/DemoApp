package com.example.demoapp.Activities

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.demoapp.R
import kotlinx.android.synthetic.main.details_chooser.*
import kotlinx.android.synthetic.main.signup.*
import java.util.*

class Sign_up_Acitivty : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup)
        val USER_TYPE = intent.getStringExtra("Category")
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()

        btdate.setOnClickListener {
            val dob = DatePickerDialog(this , R.style.DialogTheme , DatePickerDialog.OnDateSetListener{
                    view, year, month, dayOfMonth ->
                btdate.text = "${dayOfMonth.toString()} ${month.toString()} ${year.toString()}"
            } , calendar.get(Calendar.YEAR) , calendar.get(Calendar.MONTH) , calendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        Continue.setOnClickListener {
            val intent = Intent( baseContext, WorkerActivity::class.java)
            startActivity(intent)
        }



//        register_now.setOnClickListener {
//            validateCredentials()
//        }
    }

//    private fun validateCredentials() {
//        if(!isValidUserName(etUserName.text.
//                toString())){
//            Toast.makeText(baseContext , "Username should contain only letters and numbers" ,Toast.LENGTH_SHORT).show()
//        }
//
//        else if(etPassword.text.toString().length < 6 ){
//            Toast.makeText(baseContext , "Password Length should be at least 6 characters long" ,Toast.LENGTH_SHORT).show()
//        }
//
//        else if(!isValidPassWord(etPassword.text.toString())){
//            Toast.makeText(baseContext , "Password should contain at least one letter or a number and a special symbol" ,Toast.LENGTH_SHORT).show()
//        }
//
//        else if(!isValidPhoneNumber(etPhoneNumber.text.toString())){
//            Toast.makeText(baseContext , "Invalid Phone Number ! " ,Toast.LENGTH_SHORT).show()
//
//        }
//    }

    private fun isValidUserName(username : String): Boolean {
        Log.e("Username" , username)
        if(username.matches(Regex("^[\\w\\d]+\$"))){
          return true
        }

        return false
    }

    private fun isValidPassWord(password : String): Boolean {
        Log.e("Password" , password)
        if(password.matches(Regex("^(([\\w]+[\\W]+)|([\\W]+[\\w]+))+\$"))){
            return true
        }
        return false
    }

    private fun isValidPhoneNumber(password : String): Boolean {
        Log.e("Password" , password)
        if(password.matches(Regex("^\\d{10}+\$"))){
            return true
        }
        return false
    }
}