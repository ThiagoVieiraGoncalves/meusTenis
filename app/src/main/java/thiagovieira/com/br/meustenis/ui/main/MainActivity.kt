package thiagovieira.com.br.meustenis.ui.main

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import thiagovieira.com.br.meustenis.R
import kotlinx.android.synthetic.main.activity_main.*
import thiagovieira.com.br.meustenis.ui.lista.ListaTenisFragment
import thiagovieira.com.br.meustenis.ui.novoTenis.NovoTenisFragment

class MainActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_lista -> {
                changeFragment(ListaTenisFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_novo-> {
                changeFragment(NovoTenisFragment())
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    fun changeFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.containerFragment, fragment)
        transaction.commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }
}
