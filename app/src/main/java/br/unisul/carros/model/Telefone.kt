package br.unisul.carros.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import br.unisul.carros.model.enuns.TipoFone

@Entity(
    tableName = "telefones_tb",
    foreignKeys = [
        ForeignKey(entity = Carro::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("contato_id"),
        onUpdate = ForeignKey.CASCADE,
        onDelete = ForeignKey.CASCADE)])
class Telefone {

    constructor(numeroFone: String, tipo: TipoFone, idContato: Long) : this() {

        this.numero = numeroFone
        this.tipo = tipo
        this.contatoId = idContato
    }
    constructor(){

    }

    @PrimaryKey(autoGenerate = true)
    var id:Long = 0

    @ColumnInfo(name = "numero_telefones")
    var numero:String = ""

    @ColumnInfo(name = "tipo_telefones")
    lateinit var tipo: TipoFone

    @ColumnInfo(name = "contato_id")
    var contatoId: Long=0

}