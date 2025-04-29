package com.example.proyectofinalandroid.network

import kotlinx.serialization.Serializable

@Serializable
data class Truck (
    val id : Int,
    val brand : Brand,
    val model : Model,
    val preci : Double,
    val owner : Person
)
