package br.unisul.listacontatos.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.unisul.listacontatos.model.Telefone

@Dao
interface TelefoneDAO {

    @Query("SELECT * FROM telefones_tb where contato_id= :contatoId LIMIT 1")
    fun buscaPrimeiroTelefoneDoContato(contatoId:Long): Telefone

    @Query("SELECT * FROM telefones_tb where contato_id= :contatoId")
    fun buscaTodosTelefones(contatoId: Long): MutableList<Telefone>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun atualiza(vararg telefones: Telefone)

    @Insert
    fun salvar(vararg telefones: Telefone)
}
