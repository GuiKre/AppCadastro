// Arquivo: network/ApiService.kt
package com.example.cadastroclientesapp.network

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiService {
    // @GET("users?page=1") define que este método fará uma requisição GET
    // para o endpoint "users?page=1"
    @GET("users?page=1")
    suspend fun getUsers(): Response<UserListResponse>

    // Este companion object cria uma única instância do Retrofit para todo o app (padrão Singleton)
    companion object {
        private const val BASE_URL = "https://reqres.in/api/"

        fun create(): ApiService {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) // Converte JSON para objetos Kotlin
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}