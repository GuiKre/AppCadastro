// Arquivo: data/ClienteDao.kt
package com.example.cadastroclientesapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ClienteDao {

    // OnConflictStrategy.REPLACE: Se tentarmos inserir um cliente com um ID que já existe,
    // ele será substituído pelo novo.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserirCliente(cliente: Cliente)

    // @Query permite que você escreva comandos SQL.
    // Esta função busca todos os clientes da tabela, ordenados por nome.
    // O Flow garante que a lista seja atualizada automaticamente na UI sempre que houver uma mudança.
    @Query("SELECT * FROM clientes_tabela ORDER BY nome ASC")
    fun buscarTodosClientes(): Flow<List<Cliente>>
}