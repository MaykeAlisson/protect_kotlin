package br.com.redesenhe.protect.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import br.com.redesenhe.protect.R
import br.com.redesenhe.protect.viewmodel.NovoRegistroViewModel
import kotlinx.android.synthetic.main.activity_novo_registro.*

class NovoRegistroActivity : AppCompatActivity(), View.OnClickListener, CustomDialogConfiguracaoSenha.CustomDialogListener {

    private lateinit var mViewModel: NovoRegistroViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_novo_registro)

        mViewModel = ViewModelProvider(this).get(NovoRegistroViewModel::class.java)

        setSupportActionBar(findViewById(R.id.activty_novo_registro_toolbar))

        supportActionBar?.apply {
            title = "Novo Registro"
        }

        // Inicializa eventos
        setListeners()
        observe()
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.activity_novo_registro_btnGerarSenha -> {
                val customDialogConfiguracaoSenha = CustomDialogConfiguracaoSenha()
                customDialogConfiguracaoSenha.show(supportFragmentManager, "Gerar Senha")
            }
            R.id.activity_novo_registro_btnSalvar -> {

            }
            R.id.activity_novo_registro_btnCancelar -> {

            }
        }
    }


    /**
     * Inicializa os eventos de click
     */
    private fun setListeners() {
        activity_novo_registro_btnGerarSenha.setOnClickListener(this)
        activity_novo_registro_btnSalvar.setOnClickListener(this)
        activity_novo_registro_btnCancelar.setOnClickListener(this)
    }

    /**
     * Observa ViewModel
     */
    private fun observe() {
    }

    fun recuperaSenhaGerada(senha: String) {
        if (senha.trim().isEmpty()) {
            Toast.makeText(this, "Senha Invalida!", Toast.LENGTH_LONG).show()
            return
        }

        activity_novo_registro_senha.setText(senha)
        activity_novo_registro_confirmaSenha.setText(senha)

    }

    override fun applyText(senhaGerada: String) {
        recuperaSenhaGerada(senhaGerada)
    }

}