package thiagovieira.com.br.meustenis.ui.novoTenis

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_novo_tenis.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import thiagovieira.com.br.meustenis.R
import thiagovieira.com.br.meustenis.api.RetrofitClient
import thiagovieira.com.br.meustenis.api.TenisAPI
import thiagovieira.com.br.meustenis.model.Tenis

class NovoTenisFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_novo_tenis, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btSalvar.setOnClickListener {
            val api = RetrofitClient.getInstance().create(TenisAPI::class.java)

            val tenis = Tenis(null,
                    inputMarca.editText?.text.toString(),
                    inputModelo.editText?.text.toString(),
                    inputTamanho.editText?.text.toString().toInt(),
                    inputUrlImagem.editText?.text.toString()
            )

            api.salvar(tenis).enqueue(object : Callback<Void> {
                override fun onFailure(call: Call<Void>?, t: Throwable?) {
                    Log.e("Tenis", t?.message)
                }

                override fun onResponse(call: Call<Void>?, response: Response<Void>?) {
                    if(response?.isSuccessful == true) {
                        Toast.makeText(context, "Sucesso", Toast.LENGTH_SHORT).show()

                        limparCampos()
                    } else {
                        Toast.makeText(context, "Deu Ruim", Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }
    }

    private fun limparCampos() {
        inputMarca.editText?.setText("")
        inputModelo.editText?.setText("")
        inputTamanho.editText?.setText("")
        inputUrlImagem.editText?.setText("")
    }

}
