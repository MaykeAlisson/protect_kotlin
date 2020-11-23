package br.com.redesenhe.protect.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import br.com.redesenhe.protect.R
import br.com.redesenhe.protect.service.constants.ProtectConstants.APP.REGISTRO_ID
import br.com.redesenhe.protect.service.constants.ProtectConstants.APP.REGISTRO_NOME

class RegistroActivity : AppCompatActivity() {

    var idRegistro: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        val budle = intent.extras
        val nome = budle?.getString(REGISTRO_NOME)
        val id = budle?.getInt(REGISTRO_ID)
        if (id != null) {
            idRegistro = id
        }

        setSupportActionBar(findViewById(R.id.activty_registro_toolbar))

        supportActionBar?.apply {
            title = nome
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_registro, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_registro_fechar -> {}
            R.id.menu_registro_mostrar_senha -> {}
            R.id.menu_registro_copiar_senha -> {}
            R.id.menu_registro_copiar_usuario -> {}
        }
        return super.onOptionsItemSelected(item)
    }
}