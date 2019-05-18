package com.example.demoapp.Activities

import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.location.Address
import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.demoapp.R
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.details_chooser.*
import kotlinx.android.synthetic.main.signup.*
import java.util.*

class Sign_up_Acitivty : AppCompatActivity(){

    private val REQUEST_IMAGE = 0

    var  PROFILE_PHOTO_URI  : Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup)
        val USER_TYPE = intent.getStringExtra("Category")
        val uid = intent.getStringExtra("UID")

        profile_photo.setOnClickListener{
            choosePhoto()
        }


        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()

        etdate.setOnFocusChangeListener { view: View, b: Boolean ->
            if(b) {
                val dob = DatePickerDialog(
                    this,
                    R.style.DialogTheme,
                    DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                        etdate.setText("${dayOfMonth.toString()} ${monthMapping(month)} ${year.toString()}")
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)).show()
            }
        }
        etdate.setOnClickListener(){
            val dob = DatePickerDialog(
                this,
                R.style.DialogTheme,
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    etdate.setText("${dayOfMonth.toString()} ${monthMapping(month)} ${year.toString()}")
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        continue_button.setOnClickListener {
            if(validateCredentials()) {

                if(PROFILE_PHOTO_URI != null){
                    uploadImageToFireBase(uid)
                }

//                val intent = Intent(baseContext, WorkersAddress::class.java)
//                startActivity(intent)
            }
        }

    }

    private fun choosePhoto() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent , REQUEST_IMAGE)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE && resultCode == Activity.RESULT_OK){
            val uriPhoto = data?.data
            profile_photo.setImageURI(uriPhoto)
            PROFILE_PHOTO_URI = uriPhoto
        }

    }

    private fun validateCredentials() : Boolean {

        if(!ContainsAplhabetsOnly(etfirstName.text.toString())){
            Toast.makeText(baseContext , "Name should contain only letters" ,Toast.LENGTH_SHORT).show()
            return false;
        }

        if(etfirstName.text.isNullOrEmpty()){
            Toast.makeText(baseContext , "Please Fill Your First Name" ,Toast.LENGTH_SHORT).show()
            return false
        }

        if(!ContainsAplhabetsOnly(etlastName.text.toString())){
            Toast.makeText(baseContext , "Name should contain only letters" ,Toast.LENGTH_SHORT).show()
            return false
        }

        else if(etdate.text.toString().equals("SELECT YOUR DOB")){
            Toast.makeText(baseContext , "Please Choose Your Date of Birth" ,Toast.LENGTH_SHORT).show()
            return false

        }
        return true
    }

    private fun ContainsAplhabetsOnly(name: String) : Boolean {
        return name.matches(Regex("^[a-zA-z]*\$"))
    }

    private fun monthMapping(month : Int) : String{
        return when(month){
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
    private fun uploadImageToFireBase(uid : String){

        val firebaseStorage = FirebaseStorage.getInstance().getReferenceFromUrl("gs://demoapp-3afde.appspot.com")
        firebaseStorage.child(uid).putFile(PROFILE_PHOTO_URI!!).addOnSuccessListener {
            Toast.makeText(baseContext , "Image uploaded" , Toast.LENGTH_SHORT).show()
        }
    }


}

class PersonDetails(
    var FirstName : String,
    var LastName  : String,
    var Age : Int,
    var DateOfBirth : String,
    var Landmark : String,
    var State : String,
    var City : String,
    var PhoneNumber : Long,
    var image_url : String
)
