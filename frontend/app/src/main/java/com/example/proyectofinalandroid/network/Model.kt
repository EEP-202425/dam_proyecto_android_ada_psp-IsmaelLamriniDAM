package com.example.proyectofinalandroid.network

import kotlinx.serialization.Serializable

@Serializable
data class Model (
    val id : Int,
    val name : String,
    val trucks : Set<Truck>
){
}