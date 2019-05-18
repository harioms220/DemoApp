package com.example.demoapp.Activities

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.demoapp.R
import kotlinx.android.synthetic.main.workers_detail.*

class WorkerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.workers_detail)

        val sharedPreferences = getSharedPreferences("mypref",Context.MODE_PRIVATE)
        val uriString = sharedPreferences.getString("PROFILE_IMAGE_URI" , null)
        if(!uriString.isNullOrEmpty()){
            detail_profile_image.setImageURI(Uri.parse(uriString))
        }
        else{
            detail_profile_image.setImageResource(R.mipmap.add_photo_round)
        }



    }
}
