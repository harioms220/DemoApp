package com.example.demoapp.Activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.demoapp.Constants.Constants.Companion.URL
import com.example.demoapp.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.worker_address.*
import okhttp3.*
import java.io.IOException

class WorkersAddress : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.worker_address)
        fillStatesAndCities()
        button_continue.setOnClickListener {
            if (validateCredentials()) {
                val intent = Intent(baseContext , WorkerActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun validateCredentials(): Boolean {
        if (etstreet.text.toString().isNullOrEmpty()) {
            Toast.makeText(baseContext, "Please fill your Landmark or Area", Toast.LENGTH_SHORT).show()
            return false
        }

        if (etPostalCode.text.toString().isNullOrEmpty()) {
            Toast.makeText(baseContext, "Please Enter your Postal Code", Toast.LENGTH_SHORT).show()
            return false
        }

        if (!isValidate(etPostalCode.text.toString())) {
            Toast.makeText(baseContext, "Postal Code is not valid", Toast.LENGTH_SHORT).show()
            return false
        }

        if (stateSpinner.selectedItem.toString().equals("Choose a State")) {
            Toast.makeText(baseContext, "Please choose your state", Toast.LENGTH_SHORT).show()
            return false
        }

        if (citySpinner.selectedItem.toString().equals("Choose a City")) {
            Toast.makeText(baseContext, "Please choose your City", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    private fun isValidate(postalCode: String): Boolean {
        return (postalCode.matches(Regex("^\\d{6}+\$")))
    }

    private fun fillStatesAndCities() {

        val okHttpClient = OkHttpClient()

        val request = Request.Builder().url(URL).build()
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {

            }

            override fun onResponse(call: Call, response: Response) {
                val jsonstring = response.body()?.string()
                Log.e("JSONString", jsonstring)
                val gson = Gson()
                val type = object : TypeToken<ArrayList<State>>() {}.type
                val list = gson.fromJson<ArrayList<State>>(jsonstring, type)
                populateSpinners(list)
            }

        })
    }


    private fun populateSpinners(statesAndCities: ArrayList<State>) {
        val states = ArrayList<String>()
        val list = ArrayList<String>()
        list.add("Choose your City")
        val state = State("Choose your State", list)

        statesAndCities.add(0, state)
        for (i in 0..statesAndCities.size - 1) {
            states.add(statesAndCities[i].state)
        }
        Log.e("States", states.toString())

        runOnUiThread {
            val stateAdapter = ArrayAdapter<String>(baseContext, android.R.layout.simple_spinner_item, states)
            stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            stateSpinner.adapter = stateAdapter
            stateSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    val list = statesAndCities[position].cities!!
                    val cityAdapter = ArrayAdapter<String>(baseContext, android.R.layout.simple_spinner_item, list)
                    cityAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
                    citySpinner.adapter = cityAdapter
                }

            }
        }

    }
}
