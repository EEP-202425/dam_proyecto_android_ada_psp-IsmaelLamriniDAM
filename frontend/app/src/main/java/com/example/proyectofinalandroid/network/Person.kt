package com.example.proyectofinalandroid.network

import kotlinx.serialization.Serializable

@Serializable
data class Person (
    val id : Int? = null,
    val name : String,
    val lastName : String,
    val mail : String,
    val password : String
)

