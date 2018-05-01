package thiagovieira.com.br.meustenis.ui.novoUsuario

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_novo_usuario.*
import thiagovieira.com.br.meustenis.R
import thiagovieira.com.br.meustenis.api.RetrofitClient
import thiagovieira.com.br.meustenis.api.UsuarioAPI
import thiagovieira.com.br.meustenis.model.Usuario
import thiagovieira.com.br.meustenis.ui.main.MainActivity


class NovoUsuarioActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_novo_usuario)

        save.setOnClickListener { cadastraUsuario() }
    }

    private fun cadastraUsuario() {
        val api = RetrofitClient
                .getInstance()
                .create(UsuarioAPI::class.java)


        val usuario = Usuario(null,
                inputNome.editText?.text.toString(),
                inputLogin.editText?.text.toString(),
                inputPassword.editText?.text.toString()
        )

        api.salvar(usuario)

        Toast.makeText(
                application.baseContext,
                "User registered successfully!",
                Toast.LENGTH_SHORT
        ).show()

        startActivity(Intent(this, MainActivity::class.java))
        this.finish()
    }
}