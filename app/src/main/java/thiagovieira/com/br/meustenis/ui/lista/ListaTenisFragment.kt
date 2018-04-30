package thiagovieira.com.br.meustenis.ui.lista


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.erro.*
import kotlinx.android.synthetic.main.fragment_lista_tenis.*
import kotlinx.android.synthetic.main.loading.*

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import thiagovieira.com.br.meustenis.R
import thiagovieira.com.br.meustenis.api.RetrofitClient
import thiagovieira.com.br.meustenis.api.TenisAPI
import thiagovieira.com.br.meustenis.model.Tenis


class ListaTenisFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_lista_tenis, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        carregarDados()
    }

    fun carregarDados(){
        val api = RetrofitClient
                .getInstance()
                .create(TenisAPI::class.java)

        loading.visibility = View.VISIBLE

        api.buscarTodos().enqueue(object : Callback<List<Tenis>> {
            override fun onFailure(call: Call<List<Tenis>>?, t: Throwable?) {
                containerErro.visibility = View.VISIBLE
                tvMensagemErro.text = t?.message
                loading.visibility = View.GONE
            }

            override fun onResponse(call: Call<List<Tenis>>?, response: Response<List<Tenis>>?) {
                if(response?.isSuccessful == true) {
                    setupLista(response?.body())
                }else{
                    containerErro.visibility = View.VISIBLE
                    tvMensagemErro.text = response?.errorBody()?.charStream()?.readText()
                }

                loading.visibility = View.GONE
            }

        })

    }

    fun setupLista(meusTenis: List<Tenis>?) {
        meusTenis.let {
            rvTenis.adapter = ListaTenisAdapter(meusTenis!!, context)
            val layoutManager = LinearLayoutManager(context)
            rvTenis.layoutManager = layoutManager
        }

    }

}// Required empty public constructor
