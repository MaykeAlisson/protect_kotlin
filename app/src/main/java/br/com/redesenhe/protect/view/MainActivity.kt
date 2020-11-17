package br.com.redesenhe.protect.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.redesenhe.protect.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.activity_main_toolbar));

        supportActionBar?.apply {
            title = "Protect"
        }
    }

    @Override
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    @Override
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
}