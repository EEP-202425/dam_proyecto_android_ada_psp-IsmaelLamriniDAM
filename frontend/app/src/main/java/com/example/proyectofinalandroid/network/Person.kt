package com.example.proyectofinalandroid.network

import kotlinx.serialization.Serializable

@Serializable
data class Person (
    val id : Int,
    val name : String,
    val lastName : String,
    val mail : String,
    val password : String
)

