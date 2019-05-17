package com.example.demoapp.Activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.example.demoapp.Constants.Constants.Companion.URL
import com.example.demoapp.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.details_chooser.*
import okhttp3.*
import java.io.IOException

class DetailChooser : AppCompatActivity() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.details_chooser)
        val adapter = ArrayAdapter.createFromResource(baseContext , R.array.workers_list, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        categories.adapter = adapter
        progress_circular.visibility = ProgressBar.VISIBLE
        fillStatesAndCities();
    }


    private fun fillStatesAndCities() {

        val okHttpClient = OkHttpClient()

        val request = Request.Builder().url(URL).build()
        okHttpClient.newCall(request).enqueue(object : Callback{
            override fun onFailure(call: Call, e: IOException) {

            }

            override fun onResponse(call: Call, response: Response) {
                val jsonstring = response.body()?.string()
                Log.e("JSONString",jsonstring)
                val gson = Gson()
                val type = object : TypeToken<ArrayList<State>>(){}.type
                val list = gson.fromJson<ArrayList<State>>(jsonstring , type)
                populateSpinners(list)
            }

        })
    }


    private fun populateSpinners(statesAndCities : List<State>){
        val states = ArrayList<String>()
        for (i in 0..statesAndCities.size - 1){
            states.add(statesAndCities[i].state)
        }
        Log.e("States" , states.toString())

        runOnUiThread {
            val stateAdapter = ArrayAdapter<String>(baseContext, android.R.layout.simple_spinner_item, states)
            stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            stateSpinner.adapter = stateAdapter
            stateSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    val list = statesAndCities[position].cities
                    val cityAdapter = ArrayAdapter<String>(baseContext , android.R.layout.simple_spinner_item , list)
                    cityAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
                    citySpinner.adapter = cityAdapter
                    progress_circular.visibility = ProgressBar.GONE
                }

            }

            button_continue.visibility = View.VISIBLE
        }

    }
}

class State(var state: String = "", var cities: ArrayList<String>? = null)