package br.unisul.listacontatos.dao

import androidx.room.*
import br.unisul.listacontatos.model.Contato

@Dao
interface ContatoDAO {
    @Insert
    fun salva(contato: Contato): Long

    @Query("SELECT * FROM contatos_tb")
    fun todos(): List<Contato>

    @Update
    fun edita(contato: Contato)

    @Delete
    fun remover(contato: Contato)
}