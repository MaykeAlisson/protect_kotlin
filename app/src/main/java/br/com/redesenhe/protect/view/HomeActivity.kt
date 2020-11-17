package br.com.redesenhe.protect.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import br.com.redesenhe.protect.R
import br.com.redesenhe.protect.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity(), View.OnClickListener,
    CustomDialogNovoGrupo.CustomDialogListener {

    private lateinit var mViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        mViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        setSupportActionBar(findViewById(R.id.activty_home_toolbar))

        supportActionBar?.apply {
            title = "Home"
        }

        // Inicializa eventos
        setListeners()
        observe()
    }

    @Override
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        return super.onCreateOptionsMenu(menu)
    }

    @Override
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_home_fechar -> {
                Toast.makeText(this, "Menu Fechar", Toast.LENGTH_LONG).show()
            }
            R.id.menu_home_mudar_senha -> {
                Toast.makeText(this, "Menu Mudar senha master", Toast.LENGTH_LONG).show()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.activity_home_btnAdicionarGrupo -> {
                openDialog()
            }
        }
    }

    fun openDialog(){
        val customDialogNovoGrupo = CustomDialogNovoGrupo()
        customDialogNovoGrupo.show(supportFragmentManager, "Adicionar Grupo")
    }

    override fun applyText(nomeGrupo: String) {
        Toast.makeText(this, "Nome do Grupo $nomeGrupo", Toast.LENGTH_LONG).show()
    }

    /**
     * Inicializa os eventos de click
     */
    private fun setListeners() {
        activity_home_btnAdicionarGrupo.setOnClickListener(this)
    }

    /**
     * Observa ViewModel
     */
    private fun observe() {}

}