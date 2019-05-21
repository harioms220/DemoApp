package com.example.demoapp.Activities

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import com.example.demoapp.R

import kotlinx.android.synthetic.main.activity_employer_main.*


// Activity to show the employer the workers he wants to hire.

class EmployerMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employer_main)
    }

}
