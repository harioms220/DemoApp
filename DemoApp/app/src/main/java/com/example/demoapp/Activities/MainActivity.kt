package com.example.demoapp.Activities

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.SyncStateContract
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

import android.text.SpannableString
import android.text.Spanned
import android.text.style.ClickableSpan
import android.view.View
import com.example.demoapp.Constants.Constants
import com.example.demoapp.R
import com.example.viewpager.Fragments.FragmentA
import com.example.viewpager.Fragments.FragmentB
import com.example.viewpager.Fragments.FragmentC
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // checking if the user has logged in previously
        val sharedPreferences = getSharedPreferences("loginStatus" , Context.MODE_PRIVATE)
        val is_first_login = sharedPreferences.getBoolean("loginStatus" , true)

        if(is_first_login){
            startAppIntroActivity()
        }
        sharedPreferences.edit().putBoolean("loginstatus" , false).apply()
        val user_type = sharedPreferences.getString("usertype" , "USER_TYPE_INVALID")

        /// code for launching the main activity depending upon the type of user

        when(user_type){
            Constants.WORKER ->{
                //code for launching workers main Activity
                val intent = Intent(baseContext , WorkerMainActivity::class.java)
                startActivity(intent)
            }
            Constants.EMPLOYER ->{
                // code for launching employers main Activity
                val intent = Intent(baseContext , WorkerMainActivity::class.java)
                startActivity(intent)
            }
            else ->{
                // code to handle invalid users

            }
        }

        finish()

    }

    private fun startAppIntroActivity() {
        val Intent = Intent(baseContext , IntroActivity::class.java)
        startActivity(intent)
    }
}

