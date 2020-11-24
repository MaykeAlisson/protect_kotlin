package br.com.redesenhe.protect.viewmodel

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.redesenhe.protect.service.model.RegistroModel
import br.com.redesenhe.protect.service.repository.RegistroRepository

class RegistroViewModel(application: Application) : AndroidViewModel(application) {

    private val mContext = application.applicationContext
    private val mRegistroRepository = RegistroRepository.getInstace(mContext)

    private val mRegistro = MutableLiveData<RegistroModel>()
    var registro: LiveData<RegistroModel> = mRegistro

    /**
     * Busca Registro
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun buscaPorId(id: Int){
        mRegistro.value = mRegistroRepository.getById(id)
    }

}