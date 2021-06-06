package br.unisul.carros.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.unisul.carros.model.Telefone

@Dao
interface TelefoneDAO {

    @Query("SELECT * FROM telefones_tb where carro_id= :carroId LIMIT 1")
    fun buscaPrimeiroTelefoneDoCarro(carroId:Long): Telefone

    @Query("SELECT * FROM telefones_tb where carro_id= :carroId")
    fun buscaTodosTelefones(carroId: Long): MutableList<Telefone>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun atualiza(vararg telefones: Telefone)

    @Insert
    fun salvar(vararg telefones: Telefone)
}
