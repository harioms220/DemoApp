package com.example.demoapp.Activities

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.demoapp.R
import com.google.firebase.database.FirebaseDatabase

//// Activity to show the worker the details provided by him during the sign up

class WorkerMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_worker_layout)
        val sharedPreferences = getSharedPreferences("uid" , Context.MODE_PRIVATE)
        val uid = sharedPreferences.getString("uid" , null)

        val firebasereference = FirebaseDatabase.getInstance().reference.child("workers")

        firebasereference.child("uid")
    }
}
