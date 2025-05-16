package com.example.proyectofinalandroid.network

import kotlinx.serialization.Serializable

@Serializable
data class Brand (
    val id : Int? = null,
    val name : String
)