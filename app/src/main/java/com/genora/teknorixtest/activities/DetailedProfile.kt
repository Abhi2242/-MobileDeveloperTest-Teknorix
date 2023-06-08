package com.genora.teknorixtest.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.genora.teknorixtest.Models.Data
import com.genora.teknorixtest.Models.ProfileData1
import com.genora.teknorixtest.R
import com.genora.teknorixtest.Services.ProfileService
import com.genora.teknorixtest.Services.ServiceBuilder
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailedProfile : AppCompatActivity() {

    private lateinit var tv_id: TextView
    private lateinit var tv_fullName: TextView
    private lateinit var tv_email: TextView
    private lateinit var ivProfileImage: ImageView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed_profile)

        tv_id = findViewById(R.id.tv_id)
        tv_fullName = findViewById(R.id.tv_fullName)
        tv_email = findViewById(R.id.tv_email)
        ivProfileImage = findViewById(R.id.iv_profileImg)

        val bundle: Bundle? = intent.extras

        if (bundle?.containsKey(ARG_PROFILE_ID)!!) {
            val id = intent.getIntExtra(ARG_PROFILE_ID, 0)
            loadDetails(id)
        }
    }

    private fun loadDetails(positionID: Int) {

        val profileService = ServiceBuilder.buildService(ProfileService::class.java)
        val requestCall = profileService.getProfileList()

        requestCall.enqueue(object : Callback<ProfileData1> {

            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call<ProfileData1>, response: Response<ProfileData1>) {
                Log.i("Response", "${response.body()}")

                if (response.isSuccessful) {
                    Log.i("Response","Success")
                    val profile = response.body()

                    val profileList: List<Data> = profile!!.data
                    val sProfile: MutableList<Data> = ArrayList()

                    for (i in profileList){
                        val fName = i.first_name
                        val lName = i.last_name
                        val id = i.id
                        val email = i.email
                        val avatar = i.avatar

                        sProfile.add(Data(avatar,email,fName,id,lName))
                    }
                    val position = positionID-1
                    tv_id.text = sProfile[position].id.toString()
                    val fullName = "${sProfile[position].first_name} ${sProfile[position].last_name}"
                    tv_fullName.text = fullName
                    tv_email.text = sProfile[position].email
                    val url = sProfile[position].avatar
                        Picasso.get()
                            .load(url)
                            .resize(350, 350)
                            .centerCrop()
                            .into(ivProfileImage)
                }
            }

            // Invoked in case of Network Error or Establishing connection with Server or Error Creating Http Request or Error Processing Http Response
            override fun onFailure(call: Call<ProfileData1>, t: Throwable) {
                Log.i("Response","Failed $t")
            }
        })
    }


    companion object {

        const val ARG_PROFILE_ID = "item_id"
    }
}
