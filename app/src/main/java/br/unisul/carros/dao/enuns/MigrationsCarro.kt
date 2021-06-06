package br.unisul.carros.dao.enuns

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import br.unisul.carros.model.enuns.TipoFone

enum class MigrationsCarro {
    MIGRATION_1_2 {
        override fun build()= object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                with(database){
                    execSQL("CREATE TABLE IF NOT EXISTS `carro_bkp` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `marca_modelo` TEXT NOT NULL, `email` TEXT NOT NULL, `proprietario` TEXT NOT NULL, `placa` TEXT NOT NULL, `renavam` TEXT NOT NULL)")
                    execSQL("CREATE TABLE IF NOT EXISTS `telefones_tb` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `numero_telefones` TEXT NOT NULL, `tipo_telefones` TEXT NOT NULL, `carro_id` INTEGER NOT NULL, FOREIGN KEY(`carro_id`) REFERENCES `carro`(`id`) ON UPDATE CASCADE ON DELETE CASCADE )")
                    execSQL("INSERT INTO carro_bkp (id, marca_modelo, email, proprietario, placa, renavam) SELECT id, marca_modelo, email, proprietario, placa, renavam FROM carro")
                    execSQL("INSERT INTO telefones_tb (numero_telefones, tipo_telefones, carro_id) SELECT telefone_carros, ?, id FROM carro",
                    arrayOf(TipoFone.RESIDENCIAL))
                    execSQL("DROP TABLE carro")
                    execSQL("ALTER TABLE carro_bkp RENAME to carro")
                }
            }

        }
    };

    abstract fun build(): Migration
}