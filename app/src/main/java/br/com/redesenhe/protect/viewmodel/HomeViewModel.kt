package br.com.redesenhe.protect.viewmodel

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.redesenhe.protect.service.listener.ValidationListener
import br.com.redesenhe.protect.service.model.GrupoModel
import br.com.redesenhe.protect.service.repository.GrupoRepository
import br.com.redesenhe.protect.service.repository.UsuarioRepository
import java.text.SimpleDateFormat
import java.util.*

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val mContext = application.applicationContext
    private val mGrupoRepository = GrupoRepository.getInstace(mContext)
    private val mUsuarioRepository = UsuarioRepository.getInstace(mContext)

    private val mCreate = MutableLiveData<ValidationListener>()
    var create: LiveData<ValidationListener> = mCreate
    private val mList = MutableLiveData<List<GrupoModel>>()
    val grupos: LiveData<List<GrupoModel>> = mList
    private val mDelete = MutableLiveData<ValidationListener>()
    var delete: LiveData<ValidationListener> = mDelete
    private val mUpdateSenha = MutableLiveData<ValidationListener>()
    var updateSenha: LiveData<ValidationListener> = mUpdateSenha

    /**
     * Criar Grupo
     */
    fun doCreate(nome: String) {

        val data = SimpleDateFormat("dd-MM-yyyy", Locale.US).format(Date())

        val grupo = GrupoModel(0, nome,  data)

        if (mGrupoRepository.save(grupo)) {
            mCreate.value = ValidationListener()
            return
        } else {
            mCreate.value = ValidationListener("Erro ao Salvar Grupo")
            return
        }
    }

    fun getAll(){
        val all = mGrupoRepository.getAll()
        if (all.isEmpty()){
            mList.value = arrayListOf()
        }

        mList.value = all
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun doUpdate(senha: String){
        if (mUsuarioRepository.update(senha)){
            mUpdateSenha.value = ValidationListener()
        }else{
            mUpdateSenha.value = ValidationListener("Erro ao Atualizar Senha")
        }
    }

    fun doDelete(id: Int){
        if (mGrupoRepository.delete(id)){
            mDelete.value = ValidationListener()
        }else{
            mDelete.value = ValidationListener("Erro ao Deletar Grupo")
        }
    }
}