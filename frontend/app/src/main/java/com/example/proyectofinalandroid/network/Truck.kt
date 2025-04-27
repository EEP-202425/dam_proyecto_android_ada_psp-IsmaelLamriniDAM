package com.example.proyectofinalandroid.network

import kotlinx.serialization.Serializable

@Serializable
data class Truck (
    val id : Int? = null,
    val brand : Brand,
    val model : Model,
    val preci : Double,
    val Owner : Person
)
