package com.example.proyectofinalandroid.api

import com.example.proyectofinalandroid.network.Person
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

private const val BASE_URL =
    "http://10.0.2.2:8080/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType())).
    baseUrl(BASE_URL).build() // AQUI CONSTRUYE LA URL, ES DECIR, BASE_URL + GET(ALL).

interface PersonApiService {
    @POST("personas")
    suspend fun createdPerson(@Body owner: Person): Response<Void>

    @PUT("personas/{id}")
    suspend fun updatePerson(@Path("id") id: Int, @Body owner: Person): Person
}

object PersonApi {
    val retrofitService : PersonApiService by lazy {
        retrofit.create(PersonApiService::class.java)
    }
}