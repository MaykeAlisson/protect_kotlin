package br.com.redesenhe.protect.viewmodel

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.redesenhe.protect.service.listener.ValidationListener
import br.com.redesenhe.protect.service.model.RegistroModel
import br.com.redesenhe.protect.service.repository.RegistroRepository

class NovoRegistroViewModel(application: Application) : AndroidViewModel(application) {

    private val mContext = application.applicationContext
    private val mRegistroRepository = RegistroRepository.getInstace(mContext)

    private val mCreate = MutableLiveData<ValidationListener>()
    var create: LiveData<ValidationListener> = mCreate
    private val mRegistroEdit = MutableLiveData<RegistroModel>()
    var registroEdit: LiveData<RegistroModel> = mRegistroEdit
    private val mUpdate = MutableLiveData<ValidationListener>()
    var update: LiveData<ValidationListener> = mUpdate

    /**
     * Criar Registro
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun doCreate(registro: RegistroModel) {
        if (mRegistroRepository.save(registro)){
            mCreate.value = ValidationListener()
            return
        }else{
            mCreate.value = ValidationListener("Erro ao Salvar Registro")
            return
        }
    }

    /**
     * Carregar Registro para Editar
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun doEdit(id: Int){
        mRegistroEdit.value = mRegistroRepository.getById(id)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun doUpdate(registro: RegistroModel){
        if (mRegistroRepository.update(registro)){
            mUpdate.value = ValidationListener()
        }else{
            mUpdate.value = ValidationListener("Erro ao Atalizar Registro")
        }
    }
}