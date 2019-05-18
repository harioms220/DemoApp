package com.example.demoapp.Activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.demoapp.R
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.otp_layout.*
import java.util.concurrent.TimeUnit

class OTPVerify : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.otp_layout)
        val phone_number = intent.getStringExtra("Number")
        Log.e("Phone_Number " , phone_number)
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
                    Log.e("Exception" , p0?.message.toString())
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
            val intent = Intent(baseContext , Sign_up_Acitivty::class.java)
            val uid = firebaseAuth.currentUser?.uid
            intent.putExtra("UID" , uid)
            startActivity(intent)
            finish()
        }.addOnFailureListener{
            Toast.makeText(baseContext , it.message , Toast.LENGTH_SHORT)
        }
    }
}
