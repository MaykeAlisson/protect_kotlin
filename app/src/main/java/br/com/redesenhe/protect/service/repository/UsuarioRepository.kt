package br.com.redesenhe.protect.service.repository

import br.com.redesenhe.protect.service.model.UsuarioModel

class UsuarioRepository {

    fun existeUsuario() : Boolean{
        return true
    }

    fun save(usuarioModel: UsuarioModel){}

    fun getUsuario() : UsuarioModel {
        val usuario = UsuarioModel("1223", "2020-11-04")
        return usuario
    }
}