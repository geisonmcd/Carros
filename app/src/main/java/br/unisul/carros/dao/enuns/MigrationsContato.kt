package br.unisul.carros.dao.enuns

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import br.unisul.carros.model.enuns.TipoFone

enum class MigrationsContato {
    MIGRATION_1_2 {
        override fun build()= object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                with(database){
                    execSQL("CREATE TABLE IF NOT EXISTS `contatos_tb_bkp` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `nome_contatos` TEXT NOT NULL, `email_contatos` TEXT NOT NULL)")
                    execSQL("CREATE TABLE IF NOT EXISTS `telefones_tb` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `numero_telefones` TEXT NOT NULL, `tipo_telefones` TEXT NOT NULL, `contato_id` INTEGER NOT NULL, FOREIGN KEY(`contato_id`) REFERENCES `contatos_tb`(`id`) ON UPDATE CASCADE ON DELETE CASCADE )")
                    execSQL("INSERT INTO contatos_tb_bkp (id, nome_contatos, email_contatos) SELECT id, nome_contatos, email_contatos FROM contatos_tb")
                    execSQL("INSERT INTO telefones_tb (numero_telefones, tipo_telefones, contato_id) SELECT telefone_contatos, ?, id FROM contatos_tb",
                    arrayOf(TipoFone.RESIDENCIAL))
                    execSQL("DROP TABLE contatos_tb")
                    execSQL("ALTER TABLE contatos_tb_bkp RENAME to contatos_tb")
                }
            }

        }
    };

    abstract fun build(): Migration
}