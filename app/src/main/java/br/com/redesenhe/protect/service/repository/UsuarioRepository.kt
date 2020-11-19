package br.com.redesenhe.protect.service.repository

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import br.com.redesenhe.protect.service.constants.ProtectConstants.SYSTEM.LOG
import br.com.redesenhe.protect.service.model.UsuarioModel
import br.com.redesenhe.protect.service.repository.DBHelper.Companion.TABELA_USUARIO
import br.com.redesenhe.protect.service.repository.DBHelper.Companion.USUARIO_COLUMN_CRIACAO
import br.com.redesenhe.protect.service.repository.DBHelper.Companion.USUARIO_COLUMN_ID
import br.com.redesenhe.protect.service.repository.DBHelper.Companion.USUARIO_COLUMN_SENHA
import br.com.redesenhe.protect.util.ChCrypto

class UsuarioRepository private constructor(context: Context) {

    private var mDBHelper: DBHelper = DBHelper(context)
    private val set: SQLiteDatabase = mDBHelper.writableDatabase
    private val get: SQLiteDatabase = mDBHelper.readableDatabase

    companion object {
        private lateinit var repository: UsuarioRepository

        fun getInstace(context: Context): UsuarioRepository {
            if (!::repository.isInitialized) {
                repository = UsuarioRepository(context)
            }
            return repository
        }
    }

    fun existeUsuario(): Boolean {
        val sql: String = String.format("SELECT * FROM %s ", TABELA_USUARIO)

        val c: Cursor = get.rawQuery(sql, null)
        val count = c.count
        c.close()
        Log.i(LOG, "DB existeUsuario")
        return count > 0
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun save(usuarioModel: UsuarioModel): Boolean {
        val cv = ContentValues()
        cv.put(USUARIO_COLUMN_SENHA, ChCrypto.aesEncrypt(usuarioModel.senha))
        cv.put(USUARIO_COLUMN_CRIACAO, usuarioModel.dataCriacao)

        try {
            set.insert(TABELA_USUARIO, null, cv)
            Log.i(LOG, "Usuario salvo com sucesso!")
        } catch (e: Exception) {
            Log.e(LOG, "Erro ao salvar usuario " + e.message)
            return false
        }

        return true
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getUsuario(): UsuarioModel? {

        var usuario: UsuarioModel? = null

        return try {
            val sql: String = String.format("SELECT * FROM %s LIMIT 1", TABELA_USUARIO)
            val cursor = get.rawQuery(sql, null)
            if (cursor != null && cursor.count > 0) {
                cursor.moveToFirst()
                val id: Long = cursor.getLong(cursor.getColumnIndex(USUARIO_COLUMN_ID))
                val senha: String = cursor.getString(cursor.getColumnIndex(USUARIO_COLUMN_SENHA))
                val dataCriacao: String = cursor.getString(cursor.getColumnIndex(USUARIO_COLUMN_CRIACAO))

                usuario = UsuarioModel(id, ChCrypto.aesDecrypt(senha), dataCriacao)
            }

            cursor?.close()
            usuario
        } catch (e: Exception) {
            Log.e(LOG, "Erro ao buscar usuario " + e.message)
            usuario
        }

    }
}