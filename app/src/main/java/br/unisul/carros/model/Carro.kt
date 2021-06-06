package br.unisul.carros.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "contatos_tb")
class Carro() : Serializable {

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    @ColumnInfo(name = "nome_contatos")
    var nome: String = ""

    @ColumnInfo(name = "proprietario")
    var proprietario: String = ""

    @ColumnInfo(name = "placa")
    var placa: String = ""

    @ColumnInfo(name = "renavam")
    var renavam: String = ""

    @ColumnInfo(name = "email_contatos")
    lateinit var email: String

    constructor(nome: String,  email: String, proprietario: String, placa: String, renavam: String) : this() {
        this.nome = nome
        this.email = email
        this.proprietario = proprietario
        this.placa = placa
        this.renavam = renavam
    }

    fun temIdValido(): Boolean {
        return id > 0
    }

    override fun toString(): String {
        return "$nome $placa $proprietario\n"
    }

    fun setCampos(nome: String, email: String, proprietario: String, placa: String,  renavam: String) {
        this.nome = nome
        this.email = email
        this.proprietario = proprietario
        this.placa = placa
        this.renavam = renavam
    }

}