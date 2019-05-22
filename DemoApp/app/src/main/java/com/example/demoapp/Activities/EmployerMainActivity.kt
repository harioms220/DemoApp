package com.example.demoapp.Activities

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.RatingBar
import android.widget.TextView
import com.example.demoapp.Constants.Constants
import com.example.demoapp.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

import kotlinx.android.synthetic.main.activity_employer_main.etCities
import kotlinx.android.synthetic.main.activity_employer_main.etStates
import kotlinx.android.synthetic.main.item_row.view.*
import okhttp3.*
import java.io.IOException


// Activity to show the employer the workers he wants to hire.

class EmployerMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employer_main)
        fillStatesAndCities()


    }

    private fun fillStatesAndCities() {

        val okHttpClient = OkHttpClient()

        val request = Request.Builder().url(Constants.URL).build()
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
            }

            override fun onResponse(call: Call, response: Response) {
                val jsonstring = response.body()?.string()
                Log.e("JSONString", jsonstring)
                val gson = Gson()
                val type = object : TypeToken<ArrayList<State>>() {}.type
                val list = gson.fromJson<ArrayList<State>>(jsonstring, type)
                populateStatesAndCities(list)

            }

        })
    }


    private fun populateStatesAndCities(statesAndCities: ArrayList<State>) {
        val states = ArrayList<String>()
        for (i in 0..statesAndCities.size - 1) {
            states.add(statesAndCities[i].state)
        }
        Log.e("States", states.toString())

        runOnUiThread {
            etStates.setOnClickListener{
                popUpStateDialog(states)
            }

            etStates.setOnFocusChangeListener{ view: View, hasFocus: Boolean ->
                if(hasFocus){
                    popUpStateDialog(states)
                }
            }

            etCities.setOnClickListener{
                popUpCitiesDialog(states , statesAndCities)
            }

            etCities.setOnFocusChangeListener{ view: View, hasFocus: Boolean ->
                if(hasFocus){
                    popUpCitiesDialog(states , statesAndCities)
                }
            }

        }

    }

    private fun popUpCitiesDialog(states: ArrayList<String>, statesAndCities: ArrayList<State>){
        val state = etStates.text.toString()
        var pos : Int = 0
        for( i in 0..states.size - 1){
            if(states[i].equals(state)){
                pos = i
            }
        }

        val cities = statesAndCities[pos].cities
        val arrayAdapter = ArrayAdapter<String>(baseContext ,android.R.layout.simple_list_item_1 , cities)
        val alertDialog = AlertDialog.Builder(this)
            .setTitle("Choose Your City")
            .setAdapter(arrayAdapter) { dialogInterface: DialogInterface, which : Int ->
                etCities.setText(cities?.get(which))
            }.create().show()

    }

    private fun popUpStateDialog(states : ArrayList<String>){
        val arrayAdapter = ArrayAdapter<String>(this ,android.R.layout.simple_list_item_1 , states)
        val alertDialog = AlertDialog.Builder(this)
            .setTitle("Choose Your State")
            .setAdapter(arrayAdapter) { dialogInterface: DialogInterface, which : Int ->
                etStates.setText(states[which])
            }.create().show()
    }
}


class MyAdapter(var list : ArrayList<Record>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        val context = p0.context
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val view = inflater.inflate(R.layout.item_row , p0 ,false)
        val viewholder = MyViewHolder(view)
        return viewholder
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {
        val record = list.get(p1)

        val viewholder = p0 as MyViewHolder

        viewholder.tvName.text = record.name
        viewholder.tvAge.text = record.age
        viewholder.ratingBar.rating = record.Ratings
    }


    inner class MyViewHolder(view : View): RecyclerView.ViewHolder(view){
        lateinit var tvName: TextView
        lateinit var tvAge : TextView
        lateinit var ratingBar: RatingBar

        init{
            tvName = view.tvName
            tvAge = view.tvAge
            ratingBar = view.ratingBar
        }
    }

}

class Record (
    var name : String,
    var age : String,
    var Ratings : Float
)