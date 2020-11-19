package br.com.redesenhe.protect.service.repository

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import br.com.redesenhe.protect.service.constants.ProtectConstants.DATA_BASE.NAME
import br.com.redesenhe.protect.service.constants.ProtectConstants.DATA_BASE.VERSION
import br.com.redesenhe.protect.service.constants.ProtectConstants.SYSTEM.LOG

class DBHelper(context: Context) : SQLiteOpenHelper(context, NAME, null, VERSION) {



    override fun onCreate(db: SQLiteDatabase) {

        try {
            db.execSQL(SQL_USUARIO)
            db.execSQL(SQL_GRUPO)
            db.execSQL(SQL_REGISTRO)
            Log.d(LOG, "Sucesso ao criar a tabelas")
        } catch (e: Exception) {
            Log.d(LOG, "Erro ao criar a tabelas" + e.message)
        }

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    companion object {
        const val TABELA_USUARIO = "usuario"
        const val USUARIO_COLUMN_ID = "id"
        const val USUARIO_COLUMN_SENHA = "senha"
        const val USUARIO_COLUMN_CRIACAO = "criacao"

        const val TABELA_GRUPO = "grupo"
        const val GRUPO_COLUMN_ID = "id"
        const val GRUPO_COLUMN_NOME = "nome"
        const val GRUPO_COLUMN_CRIACAO = "criacao"

        const val TABELA_REGISTRO = "registro"
        const val REGISTRO_COLUMN_ID = "id"
        const val REGISTRO_COLUMN_NOME = "nome"
        const val REGISTRO_COLUMN_USUARIO = "usuario"
        const val REGISTRO_COLUMN_URL = "url"
        const val REGISTRO_COLUMN_SENHA = "senha"
        const val REGISTRO_COLUMN_COMENTARIO = "comentario"
        const val REGISTRO_COLUMN_CRIACAO = "criacao"
        const val REGISTRO_COLUMN_ID_GRUPO = "id_grupo"

        private val SQL_USUARIO = String.format("CREATE TABLE IF NOT EXISTS %s " +
                "( %s INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                " %s TEXT NOT NULL, " +
                " %s TEXT NOT NULL); ",
                TABELA_USUARIO, USUARIO_COLUMN_ID, USUARIO_COLUMN_SENHA, USUARIO_COLUMN_CRIACAO)

        private val SQL_GRUPO = String.format("CREATE TABLE IF NOT EXISTS %s " +
                "( %s INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                " %s TEXT NOT NULL," +
                " %s TEXT NOT NULL);",
                TABELA_GRUPO, GRUPO_COLUMN_ID, GRUPO_COLUMN_NOME, GRUPO_COLUMN_CRIACAO)

        private val SQL_REGISTRO = String.format("CREATE TABLE IF NOT EXISTS %s " +
                "( %s INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                " %s TEXT NOT NULL, " +
                " %s TEXT , " +
                " %s TEXT , " +
                " %s TEXT NOT NULL, " +
                " %s TEXT , " +
                " %s INTEGER ," +
                " %s TEXT NOT NULL," +
                " FOREIGN KEY(%s) REFERENCES %s(%s)); ",
                TABELA_REGISTRO, REGISTRO_COLUMN_ID, REGISTRO_COLUMN_NOME,
                REGISTRO_COLUMN_USUARIO, REGISTRO_COLUMN_URL, REGISTRO_COLUMN_SENHA,
                REGISTRO_COLUMN_COMENTARIO, REGISTRO_COLUMN_ID_GRUPO, REGISTRO_COLUMN_CRIACAO,
                REGISTRO_COLUMN_ID_GRUPO, TABELA_GRUPO, GRUPO_COLUMN_ID)

    }


}