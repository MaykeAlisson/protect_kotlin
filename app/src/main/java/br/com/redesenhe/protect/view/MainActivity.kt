package br.com.redesenhe.protect.view

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.InputType
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.com.redesenhe.protect.R
import br.com.redesenhe.protect.service.constants.ProtectConstants
import br.com.redesenhe.protect.service.constants.ProtectConstants.APP.VERSION
import br.com.redesenhe.protect.util.Permissions
import br.com.redesenhe.protect.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.String
import java.util.concurrent.Executor


class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mViewModel: MainViewModel
    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo


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

        // Biometria
        executor = ContextCompat.getMainExecutor(this)
        biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(
                    errorCode: Int,
                    errString: CharSequence
                ) {
                    super.onAuthenticationError(errorCode, errString)
                    Toast.makeText(
                        applicationContext,
                        "Authentication falha: $errString codigo $errorCode", Toast.LENGTH_SHORT
                    )
                        .show()
                }

                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult
                ) {
                    super.onAuthenticationSucceeded(result)
                    onLoginBiometria()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(
                        applicationContext, "Biometria Invalida",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Login com Biometria")
            .setSubtitle("Protect Login")
            .setNegativeButtonText("Use Senha")
            .build()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_main_sobre -> {
                openDialogSobre()
            }
            R.id.menu_main_baseDados -> {
                Permissions.verifyStoragePermissions(this)
                mViewModel.exportarDb()
            }
            R.id.menu_main_import_base -> {
                mViewModel.importDb()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onClick(view: View) {
        when (view.id) {
            R.id.activity_main_mostrarSenha -> {
                if (activity_main_mostrarSenha.isChecked) {
                    activity_main_textSenha.inputType = InputType.TYPE_CLASS_TEXT
                    return
                }
                activity_main_textSenha.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD

            }
            R.id.activity_main_biometria -> {
                if (activity_main_biometria.isChecked) {
                    mViewModel.setPrefBiometria(true)
                    return
                }
                mViewModel.setPrefBiometria(false)
            }
            R.id.activity_main_btnEntrar -> {
                if (activity_main_biometria.isChecked) {
                    biometricPrompt.authenticate(promptInfo)
                } else {
                    handleLogin()
                }
            }
        }
    }

    private fun openDialogSobre() {
        val textoSobre = String.format(
            """Protect Password e um gerenciador de senha. 
    Versão: %s 
    Desenvolvido por: Redesenhe """, VERSION
        )

        val alerta: AlertDialog
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Sobre")
        builder.setMessage(textoSobre)
        alerta = builder.create()
        alerta.show()
    }

    /**
     * Inicializa os eventos de click
     */
    private fun setListeners() {
        activity_main_btnEntrar.setOnClickListener(this)
        activity_main_mostrarSenha.setOnClickListener(this)
    }

    /**
     * Observa ViewModel
     */
    private fun observe() {
        mViewModel.login.observe(this, Observer {
            if (it.success()) {
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            } else {
                val msg = it.falure()
                Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
            }
        })
        mViewModel.exportDb.observe(this, Observer {
            if (it.success()) {
                Toast.makeText(applicationContext, "Backup criado ", Toast.LENGTH_SHORT).show()
            } else {
                val msg = it.falure()
                Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
            }
        })
        mViewModel.importDb.observe(this, Observer {
            if (it.success()) {
                Toast.makeText(applicationContext, "Backup restaurado ", Toast.LENGTH_SHORT).show()
            } else {
                val msg = it.falure()
                Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
            }
        })
        mViewModel.useBiometria.observe(this, Observer {
            activity_main_biometria.isChecked = it
        })
    }

    /**
     * Autentica usuário
     */
    @RequiresApi(Build.VERSION_CODES.O)
    private fun handleLogin() {
        val senha = activity_main_textSenha.text.toString()

        if (senha.trim().isEmpty()) {
            activity_main_textSenha.error = "Senha Obrigatoria!"
            return
        }
        mViewModel.doLogin(senha)
    }

    private fun onLoginBiometria(){
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }

}