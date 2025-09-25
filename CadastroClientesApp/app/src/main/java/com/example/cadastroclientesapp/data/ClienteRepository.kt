// Arquivo: data/ClienteRepository.kt
package com.example.cadastroclientesapp.data

import android.util.Log
import com.example.cadastroclientesapp.network.ApiService
import kotlinx.coroutines.flow.Flow

class ClienteRepository(private val clienteDao: ClienteDao) {

    // Expõe o Flow do DAO para que a camada de UI possa observá-lo.
    val todosClientes: Flow<List<Cliente>> = clienteDao.buscarTodosClientes()

    private val apiService = ApiService.create()

    // Função para buscar novos clientes da API e salvar no banco.
    suspend fun atualizarClientesDaApi() {
        try {
            val response = apiService.getUsers()
            if (response.isSuccessful) {
                val userList = response.body()?.data ?: emptyList()
                userList.forEach { user ->
                    val cliente = Cliente(
                        id = user.id,
                        nome = "${user.first_name} ${user.last_name}",
                        email = user.email
                    )
                    clienteDao.inserirCliente(cliente)
                }
            }
        } catch (e: Exception) {
            // Em um app real, aqui trataríamos o erro (ex: mostrar uma mensagem para o usuário)
            Log.e("ClienteRepository", "Falha ao buscar dados da API", e)
        }
    }

    // Função para adicionar um novo cliente.
    suspend fun adicionarCliente(cliente: Cliente) {
        // Em um app completo, aqui faríamos um POST para a API e depois salvaríamos no Room.
        // Por simplicidade, vamos apenas salvar localmente.
        clienteDao.inserirCliente(cliente)
    }
}