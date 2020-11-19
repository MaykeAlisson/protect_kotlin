package br.com.redesenhe.protect.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.redesenhe.protect.service.listener.ValidationListener
import br.com.redesenhe.protect.service.model.GrupoModel
import br.com.redesenhe.protect.service.repository.GrupoRepository
import br.com.redesenhe.protect.service.repository.UsuarioRepository

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val mContext = application.applicationContext
    private val mUsuarioRepository = UsuarioRepository.getInstace(mContext)
    private val mGrupoRepository = GrupoRepository.getInstace(mContext)

    private val mCreate = MutableLiveData<ValidationListener>()
    var create: LiveData<ValidationListener> = mCreate

    /**
     * Criar Grupo
     */
    fun doCreate(nome: String) {

        val grupo = GrupoModel(0, nome, "")

        if (mGrupoRepository.save(grupo)) {
            mCreate.value = ValidationListener()
            return
        } else {
            mCreate.value = ValidationListener("Erro ao Salvar Grupo")
            return
        }
    }
}