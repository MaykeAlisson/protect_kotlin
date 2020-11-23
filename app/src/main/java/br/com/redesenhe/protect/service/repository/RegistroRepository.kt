package br.com.redesenhe.protect.service.repository

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import br.com.redesenhe.protect.service.constants.ProtectConstants
import br.com.redesenhe.protect.service.model.RegistroModel
import br.com.redesenhe.protect.service.repository.DBHelper.Companion.REGISTRO_COLUMN_COMENTARIO
import br.com.redesenhe.protect.service.repository.DBHelper.Companion.REGISTRO_COLUMN_CRIACAO
import br.com.redesenhe.protect.service.repository.DBHelper.Companion.REGISTRO_COLUMN_ID_GRUPO
import br.com.redesenhe.protect.service.repository.DBHelper.Companion.REGISTRO_COLUMN_NOME
import br.com.redesenhe.protect.service.repository.DBHelper.Companion.REGISTRO_COLUMN_SENHA
import br.com.redesenhe.protect.service.repository.DBHelper.Companion.REGISTRO_COLUMN_URL
import br.com.redesenhe.protect.service.repository.DBHelper.Companion.REGISTRO_COLUMN_USUARIO
import br.com.redesenhe.protect.util.ChCrypto

class RegistroRepository private constructor(context: Context) {

    private var mDBHelper: DBHelper = DBHelper(context)
    private val set: SQLiteDatabase = mDBHelper.writableDatabase
    private val get: SQLiteDatabase = mDBHelper.readableDatabase

    companion object {
        private lateinit var repository: RegistroRepository

        fun getInstace(context: Context): RegistroRepository {
            if (!::repository.isInitialized) {
                repository = RegistroRepository(context)
            }
            return repository
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun save(registro: RegistroModel): Boolean{
        val cv = ContentValues()
        cv.put(REGISTRO_COLUMN_NOME, registro.nome)
        cv.put(REGISTRO_COLUMN_USUARIO, registro.usuario)
        cv.put(REGISTRO_COLUMN_URL, registro.url)
        cv.put(REGISTRO_COLUMN_SENHA, ChCrypto.aesEncrypt(registro.senha))
        cv.put(REGISTRO_COLUMN_COMENTARIO, registro.comentario)
        cv.put(REGISTRO_COLUMN_ID_GRUPO, registro.idGrupo)
        cv.put(REGISTRO_COLUMN_CRIACAO, registro.dataCriacao)

        return try {
            set.insert(DBHelper.TABELA_REGISTRO, null, cv)
            Log.i(ProtectConstants.SYSTEM.LOG, "Registro salvo com sucesso!")
            true
        } catch (e: Exception) {
            Log.e(ProtectConstants.SYSTEM.LOG, "Erro ao salvar Registro " + e.message)
            false
        }
    }

//    fun update(registro: RegistroModel): Boolean {
//        val cv = ContentValues()
//        cv.put(REGISTRO_COLUMN_NOME, registro.getNome())
//        cv.put(REGISTRO_COLUMN_USUARIO, registro.getUsuario())
//        cv.put(REGISTRO_COLUMN_URL, registro.getUrl())
//        cv.put(REGISTRO_COLUMN_SENHA, encriptar(registro.getSenha()))
//        cv.put(REGISTRO_COLUMN_COMENTARIO, registro.getComentario())
//        cv.put(REGISTRO_COLUMN_ID_GRUPO, registro.getIdGrupo())
//        cv.put(REGISTRO_COLUMN_CRIACAO, registro.getDataCriacao())
//
//        return try {
//            val args = arrayOf<String>(registro.getId().toString())
//            set.update(TABELA_REGISTRO, cv, "id=?", args)
//            Log.i(LOG, "Registro atualizada com sucesso!")
//            true
//        } catch (e: Exception) {
//            Log.e(LOG, "Erro ao atualizada Registro " + e.message)
//            false
//        }
//    }
}