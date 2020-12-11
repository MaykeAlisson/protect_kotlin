package br.com.redesenhe.protect.service.constants

/**
 * Constantes usadas na aplicação
 */
class ProtectConstants private constructor(){

    object APP {
        const val NAME = "Protect"
        const val VERSION = "1.1.0"
        const val GRUPO_ID = "id_grupo"
        const val GRUPO_NOME = "nome_grupo"
        const val REGISTRO_ID = "id_registro"
        const val REGISTRO_NOME = "nome_registro"
        const val USE_BIOMETRIA = "biometria"
    }

    object DATA_BASE {
        const val NAME = "protect.db"
        const val VERSION = 1
    }

    object SYSTEM {
        const val LOG = "protect_log"
        const val REQUEST_CODE_PERMISSIONS = 2
    }

}