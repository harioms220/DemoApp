package com.example.demoapp.Activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.SyncStateContract
import android.util.Log
import android.widget.Toast
import com.example.demoapp.Constants.Constants
import com.example.demoapp.R
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.otp_layout.*
import java.util.concurrent.TimeUnit

class OTPVerify : AppCompatActivity() {

    lateinit var usertype: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.otp_layout)
        usertype = intent.getStringExtra("usertype")
        authenticateUser()

        ChangeNumber.setOnClickListener{
            val intent = Intent(baseContext , PhoneNumberAuthentication::class.java)
            intent.putExtra("usertype" , usertype)
            startActivity(intent)
            finish()
        }


    }

    private fun authenticateUser() {
        val phone_number = intent.getStringExtra("Number")
        Log.e("Phone_Number ", phone_number)
        val phoneAuthProvider = PhoneAuthProvider.getInstance()
        phoneAuthProvider.verifyPhoneNumber(
            phone_number,
            60,
            TimeUnit.SECONDS,
            this,
            object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(credentials: PhoneAuthCredential?) {
                    signInWithPhoneAuthCredential(credentials!!)
                }

                override fun onVerificationFailed(p0: FirebaseException?) {
                    Toast.makeText(
                        baseContext,
                        " Unable to verify the phone number\n Please Try Again",
                        Toast.LENGTH_SHORT
                    ).show()
                    val intent = Intent(baseContext, PhoneNumberAuthentication::class.java)
                    startActivity(intent)
                    finish()
                }

                override fun onCodeSent(verificationID: String?, p1: PhoneAuthProvider.ForceResendingToken?) {
                    Toast.makeText(baseContext, "OTP Sent", Toast.LENGTH_SHORT).show()
                    log_in.setOnClickListener {
                        if (!etOTP.text.toString().isNullOrEmpty()) {
                            val credential =
                                PhoneAuthProvider.getCredential(verificationID!!, etOTP.text.toString())
                            signInWithPhoneAuthCredential(credential)
                        }
                    }
                }
            })
    }

    fun signInWithPhoneAuthCredential(phoneAuthCredential: PhoneAuthCredential) {
        var firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signInWithCredential(phoneAuthCredential).addOnSuccessListener {
            val uid = firebaseAuth.currentUser?.uid
            val sharedPreferences = getSharedPreferences("demopref", Context.MODE_PRIVATE)
            val phone_number = intent.getStringExtra("Number")
            sharedPreferences.edit().putString("uid", uid)
                .putBoolean("loginstatus" , false)
                .putString("usertype" , usertype)
                .putString("phonenumber" , phone_number)
                .apply()

            if(usertype == Constants.WORKER) {
                val intent = Intent(baseContext, WorkerMainActivity::class.java)
                startActivity(intent)
            }

            else{
                val intent = Intent(baseContext, EmployerMainActivity::class.java)
                startActivity(intent)
            }
        }.addOnFailureListener {
            Toast.makeText(baseContext, it.message, Toast.LENGTH_SHORT).show()
        }
    }
}
