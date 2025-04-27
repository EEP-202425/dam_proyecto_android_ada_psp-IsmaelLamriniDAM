package com.example.proyectofinalandroid.network.api

import com.example.proyectofinalandroid.network.Truck
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

private const val BASE_URL =
    "https://10.0.2.2:8080/"


private val retrofit = Retrofit.Builder()
    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType())).
    baseUrl(BASE_URL).build() // AQUI CONSTRUYE LA URL, ES DECIR, BASE_URL + GET(ALL).

interface TruckApiService {

    @POST("camiones")
    suspend fun createTruck(@Body truck: Truck) : Truck

    @GET("camiones")
    suspend fun getTrucks() : List<Truck>

    @GET("camiones/{id}")
    suspend fun getTruckById(@Path("id") id: Int) : Truck

}

object TruckApi {
    val retrofitService : TruckApiService by lazy {
        retrofit.create(TruckApiService::class.java)
    }
}