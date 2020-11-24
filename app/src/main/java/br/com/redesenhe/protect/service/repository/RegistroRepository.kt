package br.com.redesenhe.protect.service.repository

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import br.com.redesenhe.protect.service.constants.ProtectConstants.SYSTEM.LOG
import br.com.redesenhe.protect.service.model.RegistroModel
import br.com.redesenhe.protect.service.repository.DBHelper.Companion.REGISTRO_COLUMN_COMENTARIO
import br.com.redesenhe.protect.service.repository.DBHelper.Companion.REGISTRO_COLUMN_CRIACAO
import br.com.redesenhe.protect.service.repository.DBHelper.Companion.REGISTRO_COLUMN_ID
import br.com.redesenhe.protect.service.repository.DBHelper.Companion.REGISTRO_COLUMN_ID_GRUPO
import br.com.redesenhe.protect.service.repository.DBHelper.Companion.REGISTRO_COLUMN_NOME
import br.com.redesenhe.protect.service.repository.DBHelper.Companion.REGISTRO_COLUMN_SENHA
import br.com.redesenhe.protect.service.repository.DBHelper.Companion.REGISTRO_COLUMN_URL
import br.com.redesenhe.protect.service.repository.DBHelper.Companion.REGISTRO_COLUMN_USUARIO
import br.com.redesenhe.protect.service.repository.DBHelper.Companion.TABELA_REGISTRO
import br.com.redesenhe.protect.util.ChCrypto
import java.lang.String

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
    fun save(registro: RegistroModel): Boolean {
        val cv = ContentValues()
        cv.put(REGISTRO_COLUMN_NOME, registro.nome)
        cv.put(REGISTRO_COLUMN_USUARIO, registro.usuario)
        cv.put(REGISTRO_COLUMN_URL, registro.url)
        cv.put(REGISTRO_COLUMN_SENHA, ChCrypto.aesEncrypt(registro.senha))
        cv.put(REGISTRO_COLUMN_COMENTARIO, registro.comentario)
        cv.put(REGISTRO_COLUMN_ID_GRUPO, registro.idGrupo)
        cv.put(REGISTRO_COLUMN_CRIACAO, registro.dataCriacao)

        return try {
            set.insert(TABELA_REGISTRO, null, cv)
            Log.i(LOG, "Registro salvo com sucesso!")
            true
        } catch (e: Exception) {
            Log.e(LOG, "Erro ao salvar Registro " + e.message)
            false
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getById(id: Int): RegistroModel {
        val sql = String.format("SELECT *" +
                " FROM %s" +
                " WHERE %s = %s;",
                TABELA_REGISTRO, REGISTRO_COLUMN_ID, id)

        val c = get.rawQuery(sql, null)

        c.moveToFirst()

        val id = c.getInt(c.getColumnIndex(REGISTRO_COLUMN_ID))
        val nome = c.getString(c.getColumnIndex(REGISTRO_COLUMN_NOME))
        val usuario = c.getString(c.getColumnIndex(REGISTRO_COLUMN_USUARIO))
        val url = c.getString(c.getColumnIndex(REGISTRO_COLUMN_URL))
        val senha = c.getString(c.getColumnIndex(REGISTRO_COLUMN_SENHA))
        val comentario = c.getString(c.getColumnIndex(REGISTRO_COLUMN_COMENTARIO))
        val grupo = c.getInt(c.getColumnIndex(REGISTRO_COLUMN_ID_GRUPO))
        val data = c.getString(c.getColumnIndex(REGISTRO_COLUMN_CRIACAO))

        val registro = RegistroModel(
                id,
                nome,
                usuario,
                url,
                ChCrypto.aesDecrypt(senha),
                comentario,
                grupo,
                data
        )

        c?.close()

        return registro
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getAllByGrupo(idGrupo: Int): List<RegistroModel> {
        val list: MutableList<RegistroModel> = ArrayList()

        return try {
            val sql = String.format("SELECT *" +
                    " FROM %s" +
                    " WHERE %s = %s;",
                    TABELA_REGISTRO, REGISTRO_COLUMN_ID_GRUPO, idGrupo)

            val c = get.rawQuery(sql, null)

            while (c.moveToNext()) {
                val id = c.getInt(c.getColumnIndex(REGISTRO_COLUMN_ID))
                val nome = c.getString(c.getColumnIndex(REGISTRO_COLUMN_NOME))
                val usuario = c.getString(c.getColumnIndex(REGISTRO_COLUMN_USUARIO))
                val url = c.getString(c.getColumnIndex(REGISTRO_COLUMN_URL))
                val senha = c.getString(c.getColumnIndex(REGISTRO_COLUMN_SENHA))
                val comentario = c.getString(c.getColumnIndex(REGISTRO_COLUMN_COMENTARIO))
                val grupo = c.getInt(c.getColumnIndex(REGISTRO_COLUMN_ID_GRUPO))
                val data = c.getString(c.getColumnIndex(REGISTRO_COLUMN_CRIACAO))

                val registro = RegistroModel(
                        id,
                        nome,
                        usuario,
                        url,
                        ChCrypto.aesDecrypt(senha),
                        comentario,
                        grupo,
                        data
                )

                list.add(registro)
            }
            c?.close()
            list
        } catch (e: Exception) {
            Log.e(LOG, "Erro ao Buscar Registro " + e.message)
            list
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