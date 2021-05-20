package br.unisul.listacontatos.ui.activity

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import br.unisul.listacontatos.R
import br.unisul.listacontatos.dao.AppDatabase
import br.unisul.listacontatos.dao.ContatoDAO
import br.unisul.listacontatos.model.Contato
import br.unisul.listacontatos.ui.adapter.ListaContatosAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListaContatosActivity : AppCompatActivity(), ConstantesActivity {
    private val dao: ContatoDAO by lazy {
        AppDatabase.getInstance(this).contadoDAO()
    }
    private val listaContatoView: ListView by lazy { findViewById(R.id.activity_lista_contatos) }
    private val adapter: ListaContatosAdapter by lazy {
        ListaContatosAdapter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_contatos)
        title = TITULO_LISTA_CONTATOS
        configurarFabNovoContato()
        configuraLista()

    }

    override fun onResume() {
        super.onResume()
        adapter.atualizarDados(dao.todos())

    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.activity_lista_contatos_menu_remover, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val itemId = item.itemId
        if (itemId == R.id.activity_lista_contatos_menu_remover_botao) {
            confirmarExclusao(item)

        }
        return super.onContextItemSelected(item)
    }

    private fun confirmarExclusao(item: MenuItem) {
        AlertDialog.Builder(this)
            .setTitle(TITULO_CONFIRMACAO_EXCLUSAO)
            .setMessage(MSG_CONFIRMACAO_EXCLUSAO)
            .setPositiveButton(NOME_BOTAO_SIM, object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    val menuInfo: AdapterView.AdapterContextMenuInfo =
                        item.menuInfo as AdapterView.AdapterContextMenuInfo
                    var contatoEscolhido: Contato = adapter.getItem(menuInfo.position)
                    //Ponto de Exclamação duplo força um NullPointerException
                    dao.remover(contatoEscolhido)
                    adapter.remove(contatoEscolhido)
                }

            })
            .setNegativeButton(NOME_BOTAO_NAO, null)
            .show()


    }

    private fun configuraLista() {
        configurarAdapter()
        configurarListinerDeCliqueNoItem()
        registerForContextMenu(listaContatoView)
    }

    private fun configurarFabNovoContato() {
        val botaoNovoContato = findViewById<FloatingActionButton>(R.id.activity_fab_novo_contato)
        botaoNovoContato.setOnClickListener {
            startActivity(Intent(this@ListaContatosActivity, FormularioContatoActivity::class.java))
        }
    }




    private fun configurarAdapter() {
        listaContatoView.adapter = adapter
    }

    private fun configurarListinerDeCliqueNoItem() {

        listaContatoView.setOnItemClickListener { parent, view, position, id ->
            val contatoEscolhido: Contato = parent.getItemAtPosition(position) as Contato
            val formularioAlteracao = Intent(this, FormularioContatoActivity::class.java)
            formularioAlteracao.putExtra(CHAVE_EXTRA_CONTATO, contatoEscolhido)
            startActivity(formularioAlteracao)
        }
    }
}


