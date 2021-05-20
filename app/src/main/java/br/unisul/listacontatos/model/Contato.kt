package br.unisul.listacontatos.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "contatos_tb")
class Contato() : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    @ColumnInfo(name = "nome_contatos")
    var nome: String = ""

    @ColumnInfo(name = "telefone_contatos")
    lateinit var telefone: String

    @ColumnInfo(name = "email_contatos")
    lateinit var email: String

    constructor(nome: String, telefone: String, email: String) : this() {
        this.nome = nome
        this.telefone = telefone
        this.email = email
    }

    fun temIdValido(): Boolean {
        return id > 0
    }

    override fun toString(): String {
        return nome
    }

    fun setCampos(nome: String, telefone: String, email: String) {
        this.nome = nome
        this.telefone = telefone
        this.email = email
    }


}