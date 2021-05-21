package br.unisul.listacontatos.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.unisul.listacontatos.dao.conversores.ConverteTipoFone
import br.unisul.listacontatos.dao.enuns.MigrationsContato
import br.unisul.listacontatos.model.Contato
import br.unisul.listacontatos.model.Telefone

@Database(entities = [Contato::class, Telefone::class], version = 2, exportSchema = false)
@TypeConverters(ConverteTipoFone::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun contadoDAO(): ContatoDAO
    abstract fun telefoneDAO(): TelefoneDAO

    companion object {
        fun getInstance(context: Context): AppDatabase {
            val database = Room.databaseBuilder(
                context,
                AppDatabase::class.java, "baseDadosContatos_DB"
            ).allowMainThreadQueries().addMigrations(MigrationsContato.MIGRATION_1_2.build()).build()
            return database
        }
    }
}