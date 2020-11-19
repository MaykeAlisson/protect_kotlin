package br.com.redesenhe.protect.viewmodel

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.redesenhe.protect.service.listener.ValidationListener
import br.com.redesenhe.protect.service.model.UsuarioModel
import br.com.redesenhe.protect.service.repository.UsuarioRepository

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val mContext = application.applicationContext
    private val mUsuarioRepository = UsuarioRepository.getInstace(mContext)

    private val mLogin = MutableLiveData<ValidationListener>()
    var login: LiveData<ValidationListener> = mLogin

    /**
     * Fazer Login
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun doLogin(senha: String){
        if (mUsuarioRepository.existeUsuario()){
            val usuario = mUsuarioRepository.getUsuario()

            if (usuario == null) {
                mLogin.value = ValidationListener("Senha invalida")
                return
            }

            if (usuario.senha == senha){
                mLogin.value = ValidationListener()
            }else{
                mLogin.value = ValidationListener("Senha invalida")
            }
        }

        val usuario = UsuarioModel(0, senha, "")
        mUsuarioRepository.save(usuario)
    }

}