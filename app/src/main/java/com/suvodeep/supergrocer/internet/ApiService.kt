package com.suvodeep.supergrocer.internet

import com.suvodeep.supergrocer.data.InternetItem
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
//import kotlinx.serialization.Serializable

const val BASE_URL = "https://training-uploads.internshala.com"

private val json = Json { ignoreUnknownKeys = true }

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(json.asConverterFactory("application/json".toMediaType())) // Fix here
    .build()

interface ApiService {
    @GET("/android/grocery_delivery_app/items.json")
    suspend fun getItems(): List<InternetItem>
}

object Api {
    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}