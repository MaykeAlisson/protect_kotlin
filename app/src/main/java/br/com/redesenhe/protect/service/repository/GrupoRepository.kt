package br.com.redesenhe.protect.service.repository

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import br.com.redesenhe.protect.service.constants.ProtectConstants.SYSTEM.LOG
import br.com.redesenhe.protect.service.model.GrupoModel
import br.com.redesenhe.protect.service.repository.DBHelper.Companion.GRUPO_COLUMN_CRIACAO
import br.com.redesenhe.protect.service.repository.DBHelper.Companion.GRUPO_COLUMN_ID
import br.com.redesenhe.protect.service.repository.DBHelper.Companion.GRUPO_COLUMN_NOME
import br.com.redesenhe.protect.service.repository.DBHelper.Companion.REGISTRO_COLUMN_ID_GRUPO
import br.com.redesenhe.protect.service.repository.DBHelper.Companion.TABELA_GRUPO
import br.com.redesenhe.protect.service.repository.DBHelper.Companion.TABELA_REGISTRO
import java.lang.String

class GrupoRepository private constructor(context: Context) {

    private var mDBHelper: DBHelper = DBHelper(context)
    private val set: SQLiteDatabase = mDBHelper.writableDatabase
    private val get: SQLiteDatabase = mDBHelper.readableDatabase

    companion object {
        private lateinit var repository: GrupoRepository

        fun getInstace(context: Context): GrupoRepository {
            if (!::repository.isInitialized) {
                repository = GrupoRepository(context)
            }
            return repository
        }
    }

    fun save(grupo: GrupoModel): Boolean {
        val cv = ContentValues()
        cv.put(GRUPO_COLUMN_NOME, grupo.nome)
        cv.put(GRUPO_COLUMN_CRIACAO, grupo.criacao)

        return try {
            set.insert(TABELA_GRUPO, null, cv)
            Log.d(LOG, "Grupo salvo com sucesso!")
            true
        } catch (e: Exception) {
            Log.e(LOG, "Erro ao salvar grupo " + e.message)
            false
        }

    }

    fun getById(id: Int): GrupoModel {

        val sql = String.format("SELECT *" +
                " FROM %s" +
                " WHERE %s = %s;",
                TABELA_GRUPO, GRUPO_COLUMN_ID, id)

        val c = get.rawQuery(sql, null)

        c.moveToFirst()
        val nome: kotlin.String = c.getString(c.getColumnIndex(GRUPO_COLUMN_NOME))
        val criacao: kotlin.String = c.getString(c.getColumnIndex(GRUPO_COLUMN_CRIACAO))

        val grupo = GrupoModel(id, nome, criacao)

        c?.close()

        Log.e(LOG, "Erro ao salvar grupo $grupo")
        return grupo

    }

    fun getAll(): List<GrupoModel> {

        val list: MutableList<GrupoModel> = ArrayList()

        return try {
            val sql = String.format("SELECT %s," +
                    " %s" +
                    " FROM %s",
                    GRUPO_COLUMN_ID, GRUPO_COLUMN_NOME, TABELA_GRUPO)

            val c = get.rawQuery(sql, null)

            while (c.moveToNext()) {
                val id = c.getInt(c.getColumnIndex(GRUPO_COLUMN_ID))
                val nome = c.getString(c.getColumnIndex(GRUPO_COLUMN_NOME))
                val grupo = GrupoModel(id, nome, "")

                list.add(grupo)
            }
            c?.close()
            list
        } catch (e: Exception) {
            Log.e(LOG, "Erro ao salvar grupo " + e.message)
            list
        }
    }

    fun update(grupo: GrupoModel): Boolean {

        return try {
            true
        } catch (e: Exception) {
            false
        }
    }

    fun delete(id: Int): Boolean{
        return try {
            val sql = String.format("DELETE " +
                    " FROM %s" +
                    " WHERE %s = %s;",
                    TABELA_REGISTRO, REGISTRO_COLUMN_ID_GRUPO, id)

            val sql2 = String.format("DELETE " +
                    " FROM %s" +
                    " WHERE %s = %s;",
                    TABELA_GRUPO, GRUPO_COLUMN_ID, id)

            set.execSQL(sql)
            set.execSQL(sql2)
            Log.d(LOG, "GRUPO_REPOSITORY - Deletando geristros e grupo com o id $id")
            true
        } catch (e: Exception) {
            Log.e(LOG, "Erro ao Deletar grupo " + e.message)
            false
        }
    }
}