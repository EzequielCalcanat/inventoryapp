package com.ezecalc.inventoryapp_mtw.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    // Base URL de la API
    private const val BASE_URL = "https://e194-189-201-8-193.ngrok-free.app"

    // Singleton para crear y compartir la instancia de Retrofit
    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()) // Convertidor JSON a Kotlin
            .build()
            .create(ApiService::class.java)
    }
}