package thiagovieira.com.br.meustenis.ui.login

import android.support.v7.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView

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
import thiagovieira.com.br.meustenis.model.Tenis
import thiagovieira.com.br.meustenis.model.Usuario
import thiagovieira.com.br.meustenis.ui.main.MainActivity

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
                containerErro.visibility = View.VISIBLE
                tvMensagemErro.text = t?.message
                loading.visibility = View.GONE
            }

            override fun onResponse(call: Call<Usuario>?, response: Response<Usuario>?) {
                if(response?.body() != null) {
                    goToMainActivity()
                }else{
                    containerErro.visibility = View.VISIBLE
                    tvMensagemErro.text = response?.errorBody()?.charStream()?.readText()
                }

                loading.visibility = View.GONE
            }

        })

    }

    private fun goToMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        this.finish()
    }
}
