package br.com.redesenhe.protect.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.redesenhe.protect.service.model.GrupoModel
import br.com.redesenhe.protect.service.repository.GrupoRepository

class ListRegistroViewModel(application: Application) : AndroidViewModel(application) {

    private val mContext = application.applicationContext
    private val mGrupoRepository = GrupoRepository.getInstace(mContext)

}

