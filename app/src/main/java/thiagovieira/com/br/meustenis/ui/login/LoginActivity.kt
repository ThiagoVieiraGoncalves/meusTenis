package thiagovieira.com.br.meustenis.ui.login

import android.support.v7.app.AppCompatActivity
import android.app.LoaderManager.LoaderCallbacks
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.TextView

import kotlinx.android.synthetic.main.activity_login.*
import thiagovieira.com.br.meustenis.R
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
        startActivity(Intent(this, MainActivity::class.java))
        this.finish()
    }
}
