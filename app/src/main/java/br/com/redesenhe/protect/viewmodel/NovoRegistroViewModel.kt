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
    private val mList = MutableLiveData<List<RegistroModel>>()
    val registros: LiveData<List<RegistroModel>> = mList

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
}