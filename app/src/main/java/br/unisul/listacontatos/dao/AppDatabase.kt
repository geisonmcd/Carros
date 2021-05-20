package br.unisul.listacontatos.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.unisul.listacontatos.model.Contato

@Database(entities = [Contato::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun contadoDAO(): ContatoDAO


    companion object {
        fun getInstance(context: Context): AppDatabase {
            val database = Room.databaseBuilder(
                context,
                AppDatabase::class.java, "baseDadosContatos_DB"
            ).allowMainThreadQueries().build()
            return database
        }
    }
}