package br.com.redesenhe.protect.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.redesenhe.protect.R
import br.com.redesenhe.protect.service.constants.ProtectConstants.APP.GRUPO_ID
import br.com.redesenhe.protect.service.constants.ProtectConstants.APP.GRUPO_NOME

class ListRegistroActivity : AppCompatActivity() {

    var idGrupo: Long? = null
    var nomeGrupo: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_registro)

        nomeGrupo = intent.getStringExtra(GRUPO_NOME)
        idGrupo = intent.getLongExtra(GRUPO_ID, 0)

        setSupportActionBar(findViewById(R.id.activty_list_registro_toolbar))

        supportActionBar?.apply {
            title = nomeGrupo
        }

    }
}