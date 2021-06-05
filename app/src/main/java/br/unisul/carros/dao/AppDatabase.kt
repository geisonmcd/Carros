package br.unisul.carros.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.unisul.carros.dao.conversores.ConverteTipoFone
import br.unisul.carros.dao.enuns.MigrationsContato
import br.unisul.carros.model.Carro
import br.unisul.carros.model.Telefone

@Database(entities = [Carro::class, Telefone::class], version = 2, exportSchema = false)
@TypeConverters(ConverteTipoFone::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun carroDAO(): CarroDAO
    abstract fun telefoneDAO(): TelefoneDAO

    companion object {
        fun getInstance(context: Context): AppDatabase {
            val database = Room.databaseBuilder(
                context,
                AppDatabase::class.java, "carrosDB"
            ).allowMainThreadQueries().addMigrations(MigrationsContato.MIGRATION_1_2.build()).build()
            return database
        }
    }
}