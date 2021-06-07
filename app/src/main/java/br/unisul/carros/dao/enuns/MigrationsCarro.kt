package br.unisul.carros.dao.enuns

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

enum class MigrationsCarro {
    MIGRATION_1_2 {
        override fun build()= object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                with(database){
                    execSQL("CREATE TABLE IF NOT EXISTS `carro_bkp` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `marca_modelo` TEXT NOT NULL, `email` TEXT NOT NULL, `proprietario` TEXT NOT NULL, `placa` TEXT NOT NULL, `renavam` TEXT, `telefone` TEXT)")
                    execSQL("INSERT INTO carro_bkp (id, marca_modelo, email, proprietario, placa, renavam, telefone) SELECT id, marca_modelo, email, proprietario, placa, renavam, telefone FROM carro")
                    execSQL("DROP TABLE carro")
                    execSQL("ALTER TABLE carro_bkp RENAME to carro")
                }
            }
        }
    };

    abstract fun build(): Migration
}