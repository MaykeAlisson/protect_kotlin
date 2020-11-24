package br.com.redesenhe.protect.view

import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.com.redesenhe.protect.R
import br.com.redesenhe.protect.service.constants.ProtectConstants.APP.REGISTRO_ID
import br.com.redesenhe.protect.service.constants.ProtectConstants.APP.REGISTRO_NOME
import br.com.redesenhe.protect.viewmodel.RegistroViewModel
import kotlinx.android.synthetic.main.activity_registro.*

class RegistroActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mViewModel: RegistroViewModel

    var idRegistro: Int = 0

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        mViewModel = ViewModelProvider(this).get(RegistroViewModel::class.java)

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

        lodData()

        // Inicializa eventos
        setListeners()
        observe()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_registro, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_registro_fechar -> {
            }
            R.id.menu_registro_mostrar_senha -> {
            }
            R.id.menu_registro_copiar_senha -> {
            }
            R.id.menu_registro_copiar_usuario -> {
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.activity_registro_btnEditar -> {
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun lodData() {
        mViewModel.buscaPorId(idRegistro)
    }

    /**
     * Inicializa os eventos de click
     */
    private fun setListeners() {
        activity_registro_btnEditar.setOnClickListener(this)
    }

    /**
     * Observa ViewModel
     */
    private fun observe() {
        mViewModel.registro.observe(this, Observer {
            activity_registro_nome.setText(it.nome)
            activity_registro_nome.isFocusable = false;
            activity_registro_usuario.setText(it.usuario)
            activity_registro_usuario.isFocusable = false;
            activity_registro_url.setText(it.url)
            activity_registro_url.isFocusable = false;
            activity_registro_senha.setText(it.senha)
            activity_registro_senha.isFocusable = false;
            activity_registro_comentario.setText(it.comentario)
            activity_registro_comentario.isFocusable = false;
            activity_registro_criacao.setText(it.dataCriacao)
            activity_registro_criacao.isFocusable = false;
        })
    }

}