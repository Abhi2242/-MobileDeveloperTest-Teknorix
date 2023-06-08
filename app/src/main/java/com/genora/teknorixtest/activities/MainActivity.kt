package com.genora.teknorixtest.activities

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.genora.teknorixtest.Models.ProfileData1
import com.genora.teknorixtest.R
import com.genora.teknorixtest.Services.ProfileService
import com.genora.teknorixtest.Services.ServiceBuilder
import com.genora.teknorixtest.adapter.ProfileAdaptor
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.*


class MainActivity : AppCompatActivity() {

    private lateinit var toolbarTitle: TextView
    private lateinit var profile_recycler_view: RecyclerView
    private var count = 0

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadData()
        toolbarTitle = findViewById(R.id.toolbar_title)
        profile_recycler_view = findViewById(R.id.profile_recycler_view)
        toolbarTitle.text = "Count is $count"
        loadDestinations()
    }

    private fun loadDestinations() {

        val profileService = ServiceBuilder.buildService(ProfileService::class.java)
        val requestCall = profileService.getProfileList()

        requestCall.enqueue(object: Callback<ProfileData1> {

            // Your status Code / "response.code()" will decide if your Http Response is a Success or Error
            // If you receive a HTTP Response, then this method is executed
            override fun onResponse(call: Call<ProfileData1>, response: Response<ProfileData1>) {

                if (response.isSuccessful) {
                    // If status code is in the range of 200's
                    Log.i("test", "Success with ${response.code()} code")
                    val dataList = response.body()!!
                    profile_recycler_view.adapter = ProfileAdaptor(dataList.data)
                }
            }

            // Invoked in case of Network Error or Establishing connection with Server or Error Creating Http Request or Error Processing Http Response
            override fun onFailure(call: Call<ProfileData1>, t: Throwable) {
                Log.i("test3", "Error")
                Toast.makeText(this@MainActivity, "Error Occurred $t", Toast.LENGTH_LONG).show()
            }
        })
    }


    private fun loadData() {
        val shareData: SharedPreferences = getSharedPreferences("Shared Data", MODE_PRIVATE)
        count = shareData.getInt("count", 0)
        if (count == 0){
            count = 1
            saveData()
        } else{
            count++
            saveData()
        }
    }


    @SuppressLint("CommitPrefEdits")
    private fun saveData() {
        val shareData: SharedPreferences = getSharedPreferences("Shared Data", MODE_PRIVATE)
        val editor: SharedPreferences.Editor = shareData.edit()
        editor.putInt("count", count)
        editor.apply()
    }
}