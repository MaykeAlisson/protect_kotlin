package br.com.redesenhe.protect.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import br.com.redesenhe.protect.R
import br.com.redesenhe.protect.viewmodel.HomeViewModel
import br.com.redesenhe.protect.viewmodel.MainViewModel

class HomeActivity : AppCompatActivity() {

    private lateinit var mViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        mViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        setSupportActionBar(findViewById(R.id.activty_home_toolbar))

        supportActionBar?.apply {
            title = "Home"
        }


    }
}