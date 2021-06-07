package br.unisul.carros.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "carro")
class Carro() : Serializable {

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    @ColumnInfo(name = "marca_modelo")
    var marcaModelo: String = ""

    @ColumnInfo(name = "proprietario")
    var proprietario: String = ""

    @ColumnInfo(name = "placa")
    var placa: String = ""

    @ColumnInfo(name = "renavam")
    var renavam: String = ""

    @ColumnInfo(name = "telefone")
    var telefone: String = ""

    @ColumnInfo(name = "email")
    lateinit var email: String

    constructor(nome: String, email: String, proprietario: String, placa: String, renavam: String, telefone: String) : this() {
        this.marcaModelo = nome
        this.email = email
        this.proprietario = proprietario
        this.placa = placa
        this.renavam = renavam
        this.telefone = telefone
    }

    fun temIdValido(): Boolean {
        return id > 0
    }

    override fun toString(): String {
        return "$marcaModelo $placa $proprietario\n"
    }

    fun setCampos(nome: String, email: String, proprietario: String, placa: String,  renavam: String, telefone: String) {
        this.marcaModelo = nome
        this.email = email
        this.proprietario = proprietario
        this.placa = placa
        this.renavam = renavam
        this.telefone = telefone
    }

}