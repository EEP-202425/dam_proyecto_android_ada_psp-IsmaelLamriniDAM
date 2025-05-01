package com.example.proyectofinalandroid.network

import kotlinx.serialization.Serializable

@Serializable
data class Person (
    val id: Int? = null,
    val name: String,
    val lastName: String? = null,
    val mail: String? = null,
    val password: String? = null
)

