package br.unisul.listacontatos.dao

import androidx.room.Dao
import androidx.room.Query
import br.unisul.listacontatos.model.Telefone

@Dao
interface TelefoneDAO {

    @Query("SELECT * FROM telefones_tb where contato_id= :contatoId LIMIT 1")
    fun buscaPrimeiroTelefoneDoContato(contatoId:Long): Telefone
}
