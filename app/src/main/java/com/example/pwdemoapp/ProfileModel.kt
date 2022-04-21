package com.example.pwdemoapp

data class ProfileModel(
    val id : Int,
    val name : String,
    val subjects : ArrayList<String>,
    val qualification : ArrayList<String>,
    val profileImage : String
)