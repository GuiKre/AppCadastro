package com.example.cadastroclientesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cadastroclientesapp.data.Cliente
import com.example.cadastroclientesapp.userInterface.ClienteViewModel
import com.example.cadastroclientesapp.ui.theme.CadastroClientesAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CadastroClientesAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Chama a nossa função principal da UI
                    ClientesApp()
                }
            }
        }
    }
}

/**
 * Esta é a função Composable principal, que constrói a tela inteira.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClientesApp(clienteViewModel: ClienteViewModel = viewModel()) {
    // Coleta a lista de clientes do ViewModel.
    // A UI será atualizada automaticamente sempre que esta lista mudar.
    val listaClientes by clienteViewModel.clientes.collectAsState()

    // Estados para controlar o texto nos campos de input
    var nome by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cadastro de Clientes") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Seção de Cadastro
            Text("Adicionar Novo Cliente", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = nome,
                onValueChange = { nome = it },
                label = { Text("Nome") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("E-mail") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    if (nome.isNotBlank() && email.isNotBlank()) {
                        clienteViewModel.adicionarCliente(nome, email)
                        // Limpa os campos após adicionar
                        nome = ""
                        email = ""
                    }
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Salvar Cliente")
            }

            Spacer(modifier = Modifier.height(24.dp))
            Divider()
            Spacer(modifier = Modifier.height(16.dp))

            // Seção da Lista de Clientes
            Text("Clientes Cadastrados", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(listaClientes) { cliente ->
                    ClienteItem(cliente = cliente)
                }
            }
        }
    }
}

/**
 * Esta é a função Composable para exibir um único item da lista.
 */
@Composable
fun ClienteItem(cliente: Cliente) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = cliente.nome,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = cliente.email,
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
}