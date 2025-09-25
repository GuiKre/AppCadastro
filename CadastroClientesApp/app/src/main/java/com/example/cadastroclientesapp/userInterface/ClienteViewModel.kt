// Arquivo: ui/ClienteViewModel.kt
package com.example.cadastroclientesapp.userInterface

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.cadastroclientesapp.data.AppDatabase
import com.example.cadastroclientesapp.data.Cliente
import com.example.cadastroclientesapp.data.ClienteRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ClienteViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ClienteRepository

    // StateFlow para expor a lista de clientes para a UI
    val clientes: StateFlow<List<Cliente>>

    init {
        val clienteDao = AppDatabase.getDatabase(application).clienteDao()
        repository = ClienteRepository(clienteDao)
        clientes = repository.todosClientes.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
        // Inicia a busca de dados da API assim que o ViewModel é criado
        buscarClientesDaApi()
    }

    fun buscarClientesDaApi() {
        viewModelScope.launch {
            repository.atualizarClientesDaApi()
        }
    }

    fun adicionarCliente(nome: String, email: String) {
        viewModelScope.launch {
            val novoCliente = Cliente(nome = nome, email = email)
            repository.adicionarCliente(novoCliente)
        }
    }
}