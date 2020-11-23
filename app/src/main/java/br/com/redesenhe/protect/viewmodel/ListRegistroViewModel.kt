package br.com.redesenhe.protect.viewmodel

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.redesenhe.protect.service.model.GrupoModel
import br.com.redesenhe.protect.service.model.RegistroModel
import br.com.redesenhe.protect.service.repository.GrupoRepository
import br.com.redesenhe.protect.service.repository.RegistroRepository

class ListRegistroViewModel(application: Application) : AndroidViewModel(application) {

    private val mContext = application.applicationContext
    private val mRegistroRepository = RegistroRepository.getInstace(mContext)

    private val mList = MutableLiveData<List<RegistroModel>>()
    val registros: LiveData<List<RegistroModel>> = mList

    @RequiresApi(Build.VERSION_CODES.O)
    fun getAllByGrupo(idGrupo: Int){
        val all = mRegistroRepository.getAllByGrupo(idGrupo)
        if (all.isEmpty()){
            mList.value = arrayListOf()
        }

        mList.value = all
    }

}

