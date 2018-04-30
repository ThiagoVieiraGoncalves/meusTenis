package thiagovieira.com.br.meustenis.ui.lista

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_tenis.view.*
import thiagovieira.com.br.meustenis.R
import thiagovieira.com.br.meustenis.model.Tenis

class ListaTenisAdapter(private val meusTenis: List<Tenis>,
                        private val context: Context)
    : RecyclerView.Adapter<ListaTenisAdapter.MeuViewHolder>() {

    override fun getItemCount(): Int {
        return meusTenis.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MeuViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_tenis, parent, false)
        return MeuViewHolder(view)
    }

    override fun onBindViewHolder(holder: MeuViewHolder?, position: Int) {
        val tenis = meusTenis[position]
        holder?.let {
            it.bindView(tenis)
        }
    }

    class MeuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(tenis: Tenis) {
            itemView.tvMarca.text = tenis.marca
            itemView.tvModelo.text = tenis.modelo
            itemView.tvTamanho.text = tenis.tamanho.toString()

            if(tenis.urlImagem.isNullOrEmpty()){
                itemView.ivFoto.setImageDrawable(
                        ContextCompat.getDrawable(itemView.context, R.drawable.error)
                )
            }else {
                Picasso.get()
                        .load(tenis.urlImagem)
                        .placeholder(R.drawable.sandclock)
                        .error(R.drawable.error)
                        .into(itemView.ivFoto)
            }

        }
    }
}