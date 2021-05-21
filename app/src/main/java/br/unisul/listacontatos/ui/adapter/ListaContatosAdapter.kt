package br.unisul.listacontatos.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import br.unisul.listacontatos.R
import br.unisul.listacontatos.dao.AppDatabase
import br.unisul.listacontatos.model.Contato


class ListaContatosAdapter(private val context: Context): BaseAdapter() {
    private val contatoLista: MutableList<Contato> = ArrayList()


    override fun getCount(): Int {
        return contatoLista.size
    }

    override fun getItem(position: Int): Contato {
        return contatoLista.get(position)
    }

    override fun getItemId(position: Int): Long {
        return contatoLista.get(position).id
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val viewCriada: View = LayoutInflater.from(context).inflate(R.layout.item_contato, parent, false)
        val contatoIncluido = contatoLista.get(position)
        atualizarDadosTextView(viewCriada, contatoIncluido)
        return viewCriada

    }

    private fun atualizarDadosTextView(viewCriada: View,contatoIncluido: Contato) {
        val nome: TextView = viewCriada.findViewById(R.id.item_contato_nome)
        nome.text = contatoIncluido.nome
        val telefone: TextView = viewCriada.findViewById(R.id.item_contato_telefone)
        val telefoneDAO = AppDatabase.getInstance(context).telefoneDAO()
        val primeiroFone = telefoneDAO.buscaPrimeiroTelefoneDoContato(contatoIncluido.id)
        telefone.text = primeiroFone.numero
    }


    fun atualizarDados(contatos: List<Contato>){
        this.contatoLista.clear()
        this.contatoLista.addAll(contatos)
        notifyDataSetChanged()

    }

    fun remove(contato: Contato) {
        this.contatoLista.remove(contato)
        notifyDataSetChanged()
    }


}