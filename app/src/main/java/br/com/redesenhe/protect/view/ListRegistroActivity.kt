package br.com.redesenhe.protect.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import br.com.redesenhe.protect.R
import br.com.redesenhe.protect.service.constants.ProtectConstants.APP.GRUPO_ID
import br.com.redesenhe.protect.service.constants.ProtectConstants.APP.GRUPO_NOME
import br.com.redesenhe.protect.viewmodel.ListRegistroViewModel

class ListRegistroActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mViewModel: ListRegistroViewModel

    var idGrupo: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_registro)

        mViewModel = ViewModelProvider(this).get(ListRegistroViewModel::class.java)


        val budle = intent.extras
        val nome = budle?.getString(GRUPO_NOME)
        val id = budle?.getInt(GRUPO_ID)
        if (id != null) {
            idGrupo = id
        }

        setSupportActionBar(findViewById(R.id.activty_list_registro_toolbar))

        supportActionBar?.apply {
            title = nome
        }

        // Inicializa eventos
        setListeners()
        observe()

    }


    private fun loadData() {

    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.fab -> {
                val intent = Intent(applicationContext, NovoRegistroActivity::class.java)
                intent.putExtra(GRUPO_ID, idGrupo)
                startActivity(intent)
            }
        }
    }

    /**
     * Inicializa os eventos de click
     */
    private fun setListeners() {
//        activity_home_btnAdicionarGrupo.setOnClickListener(this)
    }

    /**
     * Observa ViewModel
     */
    private fun observe() {
    }

}