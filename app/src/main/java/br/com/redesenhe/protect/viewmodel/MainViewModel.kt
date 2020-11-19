package br.com.redesenhe.protect.viewmodel

import android.app.Application
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.redesenhe.protect.service.constants.ProtectConstants
import br.com.redesenhe.protect.service.constants.ProtectConstants.SYSTEM.LOG
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
            Log.i(LOG, "Existe Usuario")
            val usuario = mUsuarioRepository.getUsuario()

            if (usuario == null) {
                mLogin.value = ValidationListener("Senha invalida")
                return
            }

            if (usuario.senha == senha){
                mLogin.value = ValidationListener()
                return
            }else{
                mLogin.value = ValidationListener("Senha invalida")
                return
            }
        }

        val usuario = UsuarioModel(0, senha, "")
        if (mUsuarioRepository.save(usuario)){
            mLogin.value = ValidationListener()
            return
        }else{
            mLogin.value = ValidationListener("Erro ao Salvar usuario")
            return
        }
    }

}