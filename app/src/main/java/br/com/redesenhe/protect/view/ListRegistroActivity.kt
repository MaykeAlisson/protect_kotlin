package br.com.redesenhe.protect.view

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
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
import br.com.redesenhe.protect.service.listener.RegistroListener
import br.com.redesenhe.protect.view.adapter.GrupoAdaper
import br.com.redesenhe.protect.view.adapter.RegistroAdapter
import br.com.redesenhe.protect.viewmodel.ListRegistroViewModel
import kotlinx.android.synthetic.main.activity_list_registro.*

class ListRegistroActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mViewModel: ListRegistroViewModel
    private lateinit var mListener: RegistroListener
    private val mAdapter = RegistroAdapter()

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

        val recycler = findViewById<RecyclerView>(R.id.activity_list_registro_listView)
        recycler.layoutManager=LinearLayoutManager(this)
        recycler.addItemDecoration(DividerItemDecoration(applicationContext, LinearLayout.VERTICAL))
        recycler.adapter = mAdapter

        setSupportActionBar(findViewById(R.id.activty_list_registro_toolbar))

        supportActionBar?.apply {
            title = nome
        }

        // Eventos disparados ao clicar nas linhas da RecyclerView
        mListener = object : RegistroListener {
            override fun onListClick(id: Int, nome: String) {
//                val intent = Intent(applicationContext, ListRegistroActivity::class.java)
//                val bundle = Bundle()
//                bundle.putInt(GRUPO_ID, id)
//                bundle.putString(GRUPO_NOME, nome)
//                intent.putExtras(bundle)
//                startActivity(intent)
            }

            override fun onDeleteClick(id: Int) {
            }

        }

        mAdapter.attachListener(mListener)

        // Inicializa eventos
        setListeners()
        observe()

    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.activity_list_registro_fab -> {
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
        activity_list_registro_fab.setOnClickListener(this)
    }

    /**
     * Observa ViewModel
     */
    private fun observe() {
        mViewModel.registros.observe(this, Observer {
            if (it.count() > 0) {
                mAdapter.updateList(it)
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onResume() {
        super.onResume()
        mAdapter.attachListener(mListener)
        mViewModel.getAllByGrupo(idGrupo)
    }

}