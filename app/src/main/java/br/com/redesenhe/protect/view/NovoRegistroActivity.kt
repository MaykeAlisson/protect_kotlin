package br.com.redesenhe.protect.view

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import br.com.redesenhe.protect.R
import br.com.redesenhe.protect.service.constants.ProtectConstants.APP.GRUPO_ID
import br.com.redesenhe.protect.service.constants.ProtectConstants.APP.REGISTRO_ID
import br.com.redesenhe.protect.service.model.RegistroModel
import br.com.redesenhe.protect.viewmodel.NovoRegistroViewModel
import kotlinx.android.synthetic.main.activity_novo_registro.*
import kotlinx.android.synthetic.main.activity_registro.*
import java.text.SimpleDateFormat
import java.util.*

class NovoRegistroActivity : AppCompatActivity(), View.OnClickListener, CustomDialogConfiguracaoSenha.CustomDialogListener {

    private lateinit var mViewModel: NovoRegistroViewModel

    // Put Extra
    var idGrupo: Int = 0

    var idRegistro: Int = 0
    var dataCriacao: String = ""

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_novo_registro)

        mViewModel = ViewModelProvider(this).get(NovoRegistroViewModel::class.java)

        val budle = intent.extras
        val id = budle?.getInt(GRUPO_ID)
        val idRegistroEdit = budle?.getInt(REGISTRO_ID)
        if (id != null && id != 0) {
            idGrupo = id
        }

        if (idRegistroEdit != null && idRegistroEdit != 0){
            idRegistro = idRegistroEdit
            loadData(idRegistroEdit)
        }

        setSupportActionBar(findViewById(R.id.activty_novo_registro_toolbar))

        supportActionBar?.apply {
            title = "Novo Registro"
        }

        // Inicializa eventos
        setListeners()
        observe()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onClick(view: View) {
        when (view.id) {
            R.id.activity_novo_registro_btnGerarSenha -> {
                val customDialogConfiguracaoSenha = CustomDialogConfiguracaoSenha()
                customDialogConfiguracaoSenha.show(supportFragmentManager, "Gerar Senha")
            }
            R.id.activity_novo_registro_btnSalvar -> {
                createRegistro()
            }
            R.id.activity_novo_registro_btnCancelar -> {
                finish()
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
        mViewModel.create.observe(this, Observer {
            if (it.success()) {
                finish()
            } else {
                val msg = it.falure()
                Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
            }
        })
        mViewModel.registroEdit.observe(this, Observer {

            activity_novo_registro_nome.setText(it.nome.toString())
            activity_novo_registro_usuario.setText(it.usuario)
            activity_novo_registro_url.setText(it.url)
            activity_novo_registro_senha.setText(it.senha)
            activity_novo_registro_confirmaSenha.setText(it.senha)
            activity_novo_registro_comentario.setText(it.comentario)

            idGrupo = it.idGrupo
            dataCriacao = it.dataCriacao
        })
        mViewModel.update.observe(this, Observer {
            if (it.success()) {
                finish()
            } else {
                val msg = it.falure()
                Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
            }
        })
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

    private fun validaCampos(): Boolean {
        if (activity_novo_registro_nome.text.toString().trim().isEmpty()) {
            activity_novo_registro_nome.error = "Nome obrigatorio!"
            return false
        }

        if (activity_novo_registro_senha.text.toString().trim().isEmpty()) {
            activity_novo_registro_senha.error = "Senha obrigatoria!"
            return false
        }

        val senha = activity_novo_registro_senha.text.toString()
        val confirmaSenha = activity_novo_registro_confirmaSenha.text.toString()

        if (senha != confirmaSenha) {
            activity_novo_registro_confirmaSenha.error = "Senhas diferentes"
            return false
        }

        return true
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun loadData(id: Int){
        mViewModel.doEdit(id)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createRegistro() {
        if (!validaCampos()) return

        val nome = activity_novo_registro_nome.text.toString()
        val usuario = activity_novo_registro_usuario.text.toString()
        val url = activity_novo_registro_url.text.toString()
        val senha = activity_novo_registro_senha.text.toString()
        val comentario = activity_novo_registro_comentario.text.toString()
        val criacao = SimpleDateFormat("dd-MM-yyyy").format(Date())

        if (idRegistro != 0){
            val registro = RegistroModel(idRegistro, nome, usuario, url, senha, comentario, idGrupo, dataCriacao)
            mViewModel.doUpdate(registro)
            return
        }

        val registro = RegistroModel(0, nome, usuario, url, senha, comentario, idGrupo, criacao)

        mViewModel.doCreate(registro)
    }

}