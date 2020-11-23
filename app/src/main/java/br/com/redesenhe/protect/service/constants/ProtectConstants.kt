package br.com.redesenhe.protect.service.constants

/**
 * Constantes usadas na aplicação
 */
class ProtectConstants private constructor(){

    object APP {
        const val NAME = "Protect"
        const val VERSION = "1.0.0"
        const val GRUPO_ID = "id"
        const val GRUPO_NOME = "nome"
        const val REGISTRO_ID = "id"
        const val REGISTRO_NOME = "nome"
    }

    object DATA_BASE {
        const val NAME = "db_protect"
        const val VERSION = 1
    }

    object SYSTEM {
        const val LOG = "protect_log"
    }

}