package br.com.redesenhe.protect.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.redesenhe.protect.R
import br.com.redesenhe.protect.service.constants.ProtectConstants.APP.GRUPO_ID
import br.com.redesenhe.protect.service.constants.ProtectConstants.APP.GRUPO_NOME
import br.com.redesenhe.protect.service.listener.GrupoListener
import br.com.redesenhe.protect.view.adapter.GrupoAdaper
import br.com.redesenhe.protect.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity(), View.OnClickListener,
    CustomDialogNovoGrupo.CustomDialogListener {

    private lateinit var mViewModel: HomeViewModel
    private lateinit var mListener: GrupoListener
    private val mAdapter = GrupoAdaper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        mViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        val recycler = findViewById<RecyclerView>(R.id.activity_home_listView)
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.addItemDecoration(DividerItemDecoration(applicationContext, LinearLayout.VERTICAL))
        recycler.adapter = mAdapter

        setSupportActionBar(findViewById(R.id.activty_home_toolbar))

        supportActionBar?.apply {
            title = "Home"
        }

        // Eventos disparados ao clicar nas linhas da RecyclerView
        mListener = object : GrupoListener {
            override fun onListClick(id: Int, nome: String) {
                val intent = Intent(applicationContext, ListRegistroActivity::class.java)
                val bundle = Bundle()
                bundle.putInt(GRUPO_ID, id)
                bundle.putString(GRUPO_NOME, nome)
                intent.putExtras(bundle)
                startActivity(intent)
            }

            override fun onDeleteClick(id: Int) {
            }

        }

        mAdapter.attachListener(mListener)

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
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
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
        createGrupo(nomeGrupo)
    }

    fun createGrupo(nome: String){
        if (nome.trim().isEmpty()){
            Toast.makeText(this, "Informe um nome Valido", Toast.LENGTH_LONG).show()
            return
        }

        mViewModel.doCreate(nome)
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
    private fun observe() {
        mViewModel.create.observe(this, Observer {
            if (it.success()) {
                mAdapter.attachListener(mListener)
                mViewModel.getAll()
            } else {
                val msg = it.falure()
                Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
            }
        })
        mViewModel.grupos.observe(this, Observer {
            if (it.count() > 0) {
                mAdapter.updateList(it)
            }
        })
    }

    override fun onResume() {
        super.onResume()
        mAdapter.attachListener(mListener)
        mViewModel.getAll()
    }

}