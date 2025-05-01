package com.example.proyectofinalandroid.network.api

import com.example.proyectofinalandroid.network.Truck
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.DELETE

private const val BASE_URL =
    "http://10.0.2.2:8080/"


private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build() // AQUI CONSTRUYE LA URL, ES DECIR, BASE_URL + GET(ALL).

interface TruckApiService {

    @DELETE("camiones/{id}")
    suspend fun deleteTruck(@Path("id") id: Int) : Response<Void>

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