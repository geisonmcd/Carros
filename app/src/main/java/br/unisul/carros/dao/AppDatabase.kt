package br.unisul.carros.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.unisul.carros.dao.enuns.MigrationsCarro
import br.unisul.carros.model.Carro

@Database(entities = [Carro::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun carroDAO(): CarroDAO

    companion object {
        fun getInstance(context: Context): AppDatabase {
            val database = Room.databaseBuilder(
                context,
                AppDatabase::class.java, "carrosDB"
            ).allowMainThreadQueries().addMigrations(MigrationsCarro.MIGRATION_1_2.build()).build()
            return database
        }
    }
}