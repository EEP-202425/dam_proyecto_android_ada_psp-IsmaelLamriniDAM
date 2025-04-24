package com.example.proyectofinalandroid.network.api

import com.example.proyectofinalandroid.network.Truck
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET

private const val BASE_URL =
    "https://localhost:8080/camiones"


private val retrofit = Retrofit.Builder()
    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType())).
    baseUrl(BASE_URL).build()

interface TruckApiService {
    @GET("all")
    suspend fun getTrucks() : List<Truck>
}

object TruckApi {
    val retrofitService : TruckApiService by lazy {
        retrofit.create(TruckApiService::class.java)
    }
}