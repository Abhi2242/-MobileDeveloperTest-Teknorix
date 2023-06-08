package com.genora.teknorixtest.Services

//import com.genora.teknorixtest.Models.ProfileData
import com.genora.teknorixtest.Models.ProfileData1
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ProfileService {

    @GET("api/users/")
    fun getProfileList(): Call<ProfileData1>

    @GET("api/users/{id}")
    fun getProfile(@Path("id") id: Int): Call<ProfileData1>

}