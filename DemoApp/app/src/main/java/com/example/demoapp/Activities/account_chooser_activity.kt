package com.example.demoapp.Activities

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import com.example.demoapp.Constants.Constants
import com.example.demoapp.R
import kotlinx.android.synthetic.main.account_chooser.*

class Account_chooser_activity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.account_chooser)

        worker_button.setOnClickListener{
            launchSingnUpActivity(Constants.WORKER)
        }

        employer_button.setOnClickListener{
            launchSingnUpActivity(Constants.EMPLOYER)
        }
    }

    private fun launchSingnUpActivity(category : String) {
        val intent = Intent(baseContext , Sign_up_Acitivty::class.java)
        intent.putExtra("Category" , category)
        startActivity(intent)
        finish()
    }

}