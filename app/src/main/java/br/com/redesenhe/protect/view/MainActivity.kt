package br.com.redesenhe.protect.view

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import br.com.redesenhe.protect.R
import br.com.redesenhe.protect.service.constants.ProtectConstants
import br.com.redesenhe.protect.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        setSupportActionBar(findViewById(R.id.activity_main_toolbar))

        supportActionBar?.apply {
            title = ProtectConstants.APP.NAME
        }

        // Inicializa eventos
        setListeners()
        observe()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_main_sobre -> {
                Toast.makeText(this, "Menu Sobre", Toast.LENGTH_LONG).show()
            }
            R.id.menu_main_baseDados -> {
                Toast.makeText(this, "Menu Enviar Base de Dados", Toast.LENGTH_LONG).show()
            }
            R.id.menu_main_import_base -> {
                Toast.makeText(this, "Menu Importar Base", Toast.LENGTH_LONG).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.activity_main_mostrarSenha -> {
                if(activity_main_mostrarSenha.isChecked){
                    activity_main_textSenha.setInputType(InputType.TYPE_CLASS_TEXT)
                    return
                }
                activity_main_textSenha.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)

            }
            R.id.activity_main_btnEntrar -> {
                startActivity(Intent(this, HomeActivity::class.java))
            }
        }
    }

    /**
     * Inicializa os eventos de click
     */
    private fun setListeners() {
        activity_main_btnEntrar.setOnClickListener(this)
    }

    /**
     * Observa ViewModel
     */
    private fun observe() {}

    /**
     * Autentica usuário
     */
    private fun handleLogin() {
        val senha = activity_main_textSenha.text.toString()

        if (senha.trim().isEmpty()) {
            activity_main_textSenha.error = "Senha Obrigatoria!"
            return
        }
        mViewModel.doLogin(senha)
    }
}