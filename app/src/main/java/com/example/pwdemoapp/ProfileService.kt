package com.example.pwdemoapp

import retrofit.Call
import retrofit.http.GET
import retrofit.http.Path
import retrofit.http.Query

interface ProfileService {
    @GET("easygautam/data/users")
    fun getService(
    ): Call<ArrayList<ProfileModel>>
}