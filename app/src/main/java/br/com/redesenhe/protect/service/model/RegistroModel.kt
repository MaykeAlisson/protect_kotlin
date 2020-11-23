package br.com.redesenhe.protect.service.model

class RegistroModel(
        var id: Int,
        var nome: String,
        var usuario: String,
        var url: String,
        var senha: String,
        var comentario: String,
        var idGrupo: Int,
        var dataCriacao: String
)
