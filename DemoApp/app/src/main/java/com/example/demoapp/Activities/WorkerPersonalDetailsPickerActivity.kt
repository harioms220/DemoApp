package com.example.demoapp.Activities

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.example.demoapp.R
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.worker_personal_details_picker_activity.*
import java.util.*


// Activity to pick the worker personal details including name , date of birth , etc.

class WorkerPersonalDetailsPickerActivity : AppCompatActivity() {

    private val REQUEST_IMAGE = 0

    private val SIGN_UP_COMPLETED = 1

    var PROFILE_PHOTO_URI: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.worker_personal_details_picker_activity)

        val USER_TYPE = intent.getStringExtra("Category")
        val uid = intent.getStringExtra("UID")

        profile_photo.setOnClickListener {
            choosePhoto()
        }



        etdate.setOnFocusChangeListener { view: View, hasFocus: Boolean ->
            if (hasFocus) {
                displayDatePicker()
            }
        }
        etdate.setOnClickListener() {
            displayDatePicker()
        }

        continue_button.setOnClickListener {
            if (validateCredentials()) {
                val intent = Intent(baseContext, WorkersAddressPickerActivity::class.java)
                intent.putExtra("firstname", etfirstName.text.toString())
                intent.putExtra("lastname", etlastName.text.toString())
                intent.putExtra("dateofbirth", etdate.text.toString())

                startActivityForResult(intent , SIGN_UP_COMPLETED )
            }
        }

    }

    private fun displayDatePicker() {

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()

        val dob = DatePickerDialog(
            this,
            R.style.DialogTheme,
            DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                etdate.setText("${dayOfMonth.toString()} ${monthMapping(month)} ${year.toString()}")
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun choosePhoto() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_IMAGE)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE && resultCode == Activity.RESULT_OK) {
            val uriPhoto = data?.data
            profile_photo.setImageURI(uriPhoto)
            PROFILE_PHOTO_URI = uriPhoto
        }

        if(requestCode == SIGN_UP_COMPLETED && resultCode == Activity.RESULT_OK){
            // add code for uploading the details on the firebase database

        }

    }

    private fun validateCredentials(): Boolean {

        if (!ContainsAplhabetsOnly(etfirstName.text.toString())) {
            Toast.makeText(baseContext, "Name should contain only letters", Toast.LENGTH_SHORT).show()
            return false;
        }

        if (etfirstName.text.isNullOrEmpty()) {
            Toast.makeText(baseContext, "Please Fill Your First Name", Toast.LENGTH_SHORT).show()
            return false
        }

        if (!ContainsAplhabetsOnly(etlastName.text.toString())) {
            Toast.makeText(baseContext, "Name should contain only letters", Toast.LENGTH_SHORT).show()
            return false
        } else if (etdate.text.toString().equals("SELECT YOUR DOB")) {
            Toast.makeText(baseContext, "Please Choose Your Date of Birth", Toast.LENGTH_SHORT).show()
            return false

        }
        return true
    }

    private fun ContainsAplhabetsOnly(name: String): Boolean {
        return name.matches(Regex("^[a-zA-z]*\$"))
    }


    private fun uploadImageToFireBase(uid: String) {

        val firebaseStorage = FirebaseStorage.getInstance().getReferenceFromUrl("gs://demoapp-3afde.appspot.com")
        firebaseStorage.child(uid).putFile(PROFILE_PHOTO_URI!!).addOnSuccessListener {
            Toast.makeText(baseContext, "Image uploaded", Toast.LENGTH_SHORT).show()
        }
    }


}

class PersonDetails(
    var FirstName: String,
    var LastName: String,
    var Age: Int,
    var DateOfBirth: String,
    var Landmark: String,
    var State: String,
    var City: String,
    var PhoneNumber: Long,
    var image_url: String
)

fun monthMapping(month: Int): String {
    return when (month) {
        0 -> "JAN"
        1 -> "FEB"
        2 -> "MAR"
        3 -> "APR"
        4 -> "MAY"
        5 -> "JUN"
        6 -> "JUL"
        7 -> "AUG"
        8 -> "SEP"
        9 -> "OCT"
        10 -> "NOV"
        else -> "DEC"
    }
}

fun reverseMonthMapping(month: String): Int {
    return when (month) {
        "JAN" -> 0
        "FEB" -> 1
        "MAR" -> 2
        "APR" -> 3
        "MAY" -> 4
        "JUN" -> 5
        "JUL" -> 6
        "AUG" -> 7
        "SEP" -> 8
        "OCT" -> 9
        "NOV" -> 10
        else -> 11
    }
}
