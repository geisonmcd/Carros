package br.unisul.carros.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import br.unisul.carros.R
import br.unisul.carros.dao.AppDatabase
import br.unisul.carros.model.Carro
import br.unisul.carros.model.Telefone


class ListaCarrosAdapter(private val context: Context) : BaseAdapter() {

    private val carros: MutableList<Carro> = ArrayList()

    override fun getCount(): Int {
        return carros.size
    }

    override fun getItem(position: Int): Carro {
        return carros.get(position)
    }

    override fun getItemId(position: Int): Long {
        return carros.get(position).id
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val viewCriada: View = LayoutInflater.from(context).inflate(R.layout.item_carro, parent, false)
        val contatoIncluido = carros.get(position)
        atualizarDadosTextView(viewCriada, contatoIncluido)
        return viewCriada

    }

    private fun atualizarDadosTextView(viewCriada: View, carroIncluido: Carro) {
        val nome: TextView = viewCriada.findViewById(R.id.item_contato_nome)
        nome.text = carroIncluido.nome
        val telefone: TextView = viewCriada.findViewById(R.id.item_contato_telefone)
        val telefoneDAO = AppDatabase.getInstance(context).telefoneDAO()
        val primeiroFone: Telefone? = telefoneDAO.buscaPrimeiroTelefoneDoContato(carroIncluido.id)
        if (primeiroFone != null) {
            telefone.text = primeiroFone.numero
        }
    }

    fun atualizarDados(carros: List<Carro>) {
        this.carros.clear()
        this.carros.addAll(carros)
        notifyDataSetChanged()
    }

    fun remove(carro: Carro) {
        this.carros.remove(carro)
        notifyDataSetChanged()
    }

}