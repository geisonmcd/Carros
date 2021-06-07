package br.unisul.carros.dao

import androidx.room.*
import br.unisul.carros.model.Carro

@Dao
interface CarroDAO {

    @Insert
    fun salva(carro: Carro): Long

    @Query("SELECT * FROM carro order by marca_modelo, proprietario")
    fun todos(): List<Carro>

    @Query("SELECT * FROM carro where UPPER(marca_modelo) like '%' || UPPER(:query) || '%'")
    fun buscaCarroByMarcaModelo(query: String): List<Carro>

    @Query("SELECT * FROM carro where UPPER(proprietario) like '%' || UPPER(:query) || '%'")
    fun buscaCarroByProprietario(query: String): List<Carro>

    @Update
    fun edita(carro: Carro)

    @Delete
    fun remover(carro: Carro)
}