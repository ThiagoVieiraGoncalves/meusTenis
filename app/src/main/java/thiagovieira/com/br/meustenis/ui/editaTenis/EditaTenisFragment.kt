package thiagovieira.com.br.meustenis.ui.editaTenis

import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_novo_tenis.*
import kotlinx.android.synthetic.main.loading.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import thiagovieira.com.br.meustenis.R
import thiagovieira.com.br.meustenis.api.RetrofitClient
import thiagovieira.com.br.meustenis.api.TenisAPI
import thiagovieira.com.br.meustenis.model.Tenis
import thiagovieira.com.br.meustenis.ui.lista.ListaTenisFragment

class EditaTenisFragment : Fragment() {

    lateinit var tenisResponse : Call<Tenis>
    lateinit var tenis : Tenis
    var api = RetrofitClient.getInstance().create(TenisAPI::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val id = arguments.getString("id")

        tenisResponse = api.buscaPeloId(id)
    }

    override fun onCreateView(
            inflater: LayoutInflater?,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val inflateView = inflater!!.inflate(R.layout.fragment_novo_tenis, container, false)

        return inflateView
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loading.visibility = View.VISIBLE

        tenisResponse.enqueue(object: Callback<Tenis> {
            override fun onFailure(call: Call<Tenis>, t: Throwable?) {
                Log.e("API erro", t?.message)
            }

            override fun onResponse(call: Call<Tenis>, response: Response<Tenis>?) {
                tenis = response?.body()!!

                inputMarca.editText?.setText(tenis?.marca)
                inputModelo.editText?.setText(tenis?.modelo)
                inputTamanho.editText?.setText(tenis?.tamanho.toString())
                inputUrlImagem.editText?.setText(tenis?.urlImagem)

                loading.visibility = View.GONE
                btDeletar.visibility = View.VISIBLE
            }
        })

        btSalvar.setOnClickListener {

            tenis?.marca = inputMarca.editText?.text.toString()
            tenis?.modelo = inputModelo.editText?.text.toString()
            tenis?.tamanho = inputTamanho.editText?.text.toString().toInt()
            tenis?.urlImagem = inputUrlImagem.editText?.text.toString()

            this.api.salvar(tenis!!).enqueue(object : Callback<Void> {
                override fun onFailure(call: Call<Void>?, t: Throwable?) {
                    Log.e("Tenis Update", t?.message)
                }

                override fun onResponse(call: Call<Void>?, response: Response<Void>?) {
                    if(response?.isSuccessful == true) {
                        Toast.makeText(
                                context,"Tenis salvo com sucesso", Toast.LENGTH_SHORT).show()

                        activity.onBackPressed()
                    } else {
                        Toast.makeText(
                                context, "Alguma coisa aconteceu, tente novamente mais tarde",
                                Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }

        btDeletar.setOnClickListener {
            val builder = AlertDialog.Builder(context)

            builder.setTitle("Remover")
            builder.setMessage("Você tem certeza que deseja remover?")

            builder.setPositiveButton("Sim", DialogInterface.OnClickListener {
                dialogInterface, i ->

                api.remover(tenis.id!!).enqueue(object : Callback<Void> {
                    override fun onFailure(call: Call<Void>?, t: Throwable?) {
                        Log.e("Tenis delete", t?.message)
                    }

                    override fun onResponse(call: Call<Void>?, response: Response<Void>?) {
                        dialogInterface.dismiss()
                        Toast.makeText(context, "Removido com sucesso", Toast.LENGTH_SHORT).show()

                        val activity = context as AppCompatActivity
                        val listaTenisFragment = ListaTenisFragment()

                        activity.supportFragmentManager.beginTransaction().replace(
                                R.id.containerFragment,
                                listaTenisFragment
                        ).addToBackStack(null).commit()
                    }
                })
            })
            builder.setNegativeButton("Não", DialogInterface.OnClickListener {
                dialogInterface, i ->
                dialogInterface.dismiss()
            })

            val alert = builder.create()
            alert.show()

            true
        }
    }
}