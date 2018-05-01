package thiagovieira.com.br.meustenis.ui.login

import android.support.v7.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast

import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.erro.*
import kotlinx.android.synthetic.main.fragment_novo_tenis.*
import kotlinx.android.synthetic.main.loading.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import thiagovieira.com.br.meustenis.R
import thiagovieira.com.br.meustenis.api.RetrofitClient
import thiagovieira.com.br.meustenis.api.UsuarioAPI
import thiagovieira.com.br.meustenis.model.Usuario
import thiagovieira.com.br.meustenis.ui.main.MainActivity
import thiagovieira.com.br.meustenis.ui.novoUsuario.NovoUsuarioActivity

/**
 * A login screen that offers login via email/password.
 */
class LoginActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        // Set up the login form.

        password.setOnEditorActionListener(TextView.OnEditorActionListener { _, id, _ ->
            if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                attemptLogin()
                return@OnEditorActionListener true
            }
            false
        })

        email_sign_in_button.setOnClickListener { attemptLogin() }

        register.setOnClickListener{
            startActivity(Intent(this, NovoUsuarioActivity::class.java))
        }
    }

    private fun attemptLogin() {
        val api = RetrofitClient
                .getInstance()
                .create(UsuarioAPI::class.java)


        val usuario = Usuario(null,
                null,
                inputLogin.editText?.text.toString(),
                inputPassword.editText?.text.toString()
        )

        api.login(usuario).enqueue(object : Callback<Usuario> {
            override fun onFailure(call: Call<Usuario>?, t: Throwable?) {
                Toast.makeText(
                        application.baseContext,
                        "Login/Password invalid. Try again",
                        Toast.LENGTH_SHORT
                ).show()
            }

            override fun onResponse(call: Call<Usuario>?, response: Response<Usuario>?) {
                if(response?.body() != null) {
                    goToMainActivity()
                }else{
                    Toast.makeText(
                        application.baseContext,
                        "Login/Password invalid. Try again",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        })

    }

    private fun goToMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        this.finish()
    }
}
