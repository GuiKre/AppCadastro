// Arquivo: network/ApiResponse.kt
package com.example.cadastroclientesapp.network

// A API retorna um objeto principal que contém uma lista de usuários
data class UserListResponse(
    val data: List<UserResponse>
)

// Representa um único usuário vindo da API
data class UserResponse(
    val id: Int,
    val email: String,
    val first_name: String,
    val last_name: String
)