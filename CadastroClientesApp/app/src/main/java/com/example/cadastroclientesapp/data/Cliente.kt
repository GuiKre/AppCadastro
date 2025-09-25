// Arquivo: data/Cliente.kt
package com.example.cadastroclientesapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

// A anotação @Entity diz ao Room que esta classe representa uma tabela no banco de dados.
@Entity(tableName = "clientes_tabela")
data class Cliente(
    // @PrimaryKey define que o 'id' é a chave primária da tabela.
    // autoGenerate = true faz com que o Room gere o ID automaticamente.
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val nome: String,
    val email: String
)