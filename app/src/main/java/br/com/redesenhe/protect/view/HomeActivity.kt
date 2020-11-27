package br.com.redesenhe.protect.view

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
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
import br.com.redesenhe.protect.view.adapter.GrupoAdaper
import br.com.redesenhe.protect.viewmodel.HomeViewModel
import com.afollestad.materialdialogs.MaterialDialog.SingleButtonCallback
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog
import com.github.javiersantos.materialstyleddialogs.enums.Style
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

            override fun onDeleteClick(id: Int, nome: String) {
                exibeDialogDeletaGrupo(nome, id)
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

    @RequiresApi(Build.VERSION_CODES.O)
    @Override
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_home_fechar -> {
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
            R.id.menu_home_mudar_senha -> {
                openMudarSenhaMaster()
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

    private fun exibeDialogDeletaGrupo(nomeGrupo: String, id: Int){
        val view: View = LayoutInflater.from(this).inflate(R.layout.insere_texto_alerta, null)
        val mTextAlerta = view.findViewById<TextView>(R.id.textMensagem)
        val mensagem = String.format("Ao apagar o grupo %s todos os registros do grupo sera apagado. Deseja Continuar ?", nomeGrupo)
        mTextAlerta.text = mensagem

        val dialog: MaterialStyledDialog = MaterialStyledDialog.Builder(this)
                .setTitle("Deleta Grupo")
                .setStyle(Style.HEADER_WITH_TITLE)
                .setCustomView(view, 20, 0, 20, 0)
                .setNegative("Cancelar", null)
                .setPositive("Deletar", SingleButtonCallback { dialog, which ->
                    mViewModel.doDelete(id)
                }).build()
        dialog.show()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun openMudarSenhaMaster(){
        val view: View = LayoutInflater.from(this).inflate(R.layout.layout_mudar_senha_master, null)
        val inputSenha = view.findViewById<EditText>(R.id.layout_mudar_senha_senha)
        val inputConfimaSenha = view.findViewById<EditText>(R.id.layout_mudar_senha_confirmaSenha)


        val dialog: MaterialStyledDialog = MaterialStyledDialog.Builder(this)
                .setIcon(R.drawable.icone_key)
                .setCustomView(view, 20, 20, 20, 0)
                .setNegative("Cancelar", null)
                .setPositive("Salvar", SingleButtonCallback { dialog, which ->
                    if (inputSenha.text.toString().trim().isEmpty()) {
                        Toast.makeText(applicationContext, "Senha invalida!", Toast.LENGTH_LONG).show()
                        return@SingleButtonCallback
                    }
                    if (inputSenha.text.toString() != inputConfimaSenha.text.toString()) {
                        Toast.makeText(applicationContext, "Senhas diferentes!", Toast.LENGTH_LONG).show()
                        return@SingleButtonCallback
                    }
                    val senha = inputSenha.text.toString()
                    mViewModel.doUpdate(senha)
                })
                .build()
        dialog.show()
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
            mAdapter.updateList(it)
        })
        mViewModel.delete.observe(this, Observer {
            if (it.success()) {
                mAdapter.attachListener(mListener)
                mViewModel.getAll()
            } else {
                val msg = it.falure()
                Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
            }
        })
        mViewModel.updateSenha.observe(this, Observer {
            if (it.success()) {
                Toast.makeText(applicationContext, "Senha alterada com sucesso", Toast.LENGTH_SHORT).show()
            } else {
                val msg = it.falure()
                Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onResume() {
        super.onResume()
        mAdapter.attachListener(mListener)
        mViewModel.getAll()
    }

}