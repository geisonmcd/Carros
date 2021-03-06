package br.unisul.carros.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import br.unisul.carros.R
import br.unisul.carros.model.Carro


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
        val carroIncluido = carros.get(position)
        atualizarDadosTextView(viewCriada, carroIncluido)
        return viewCriada

    }

    private fun atualizarDadosTextView(viewCriada: View, carroIncluido: Carro) {
        val marcaModeloProprietario: TextView = viewCriada.findViewById(R.id.item_carro_marca_modelo)
        marcaModeloProprietario.text = carroIncluido.marcaModelo + " - " + carroIncluido.proprietario
        val telefone: TextView = viewCriada.findViewById(R.id.item_carro_telefone)
        telefone.text = carroIncluido.telefone
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