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
        childColumns = arrayOf("carro_id"),
        onUpdate = ForeignKey.CASCADE,
        onDelete = ForeignKey.CASCADE)])
class Telefone {

    constructor(numeroFone: String, tipo: TipoFone, idCarro: Long) : this() {

        this.numero = numeroFone
        this.tipo = tipo
        this.carroId = idCarro
    }
    constructor(){

    }

    @PrimaryKey(autoGenerate = true)
    var id:Long = 0

    @ColumnInfo(name = "numero_telefones")
    var numero:String = ""

    @ColumnInfo(name = "tipo_telefones")
    lateinit var tipo: TipoFone

    @ColumnInfo(name = "carro_id")
    var carroId: Long=0

}