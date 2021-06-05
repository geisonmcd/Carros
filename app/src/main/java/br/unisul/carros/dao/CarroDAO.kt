package br.unisul.carros.dao

import androidx.room.*
import br.unisul.carros.model.Carro

@Dao
interface CarroDAO {

    @Insert
    fun salva(carro: Carro): Long

    @Query("SELECT * FROM contatos_tb")
    fun todos(): List<Carro>

    @Update
    fun edita(carro: Carro)

    @Delete
    fun remover(carro: Carro)
}