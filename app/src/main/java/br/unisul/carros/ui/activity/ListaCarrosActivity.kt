package br.unisul.carros.ui.activity

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import br.unisul.carros.R
import br.unisul.carros.dao.AppDatabase
import br.unisul.carros.dao.CarroDAO
import br.unisul.carros.model.Carro
import br.unisul.carros.ui.adapter.ListaCarrosAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListaCarrosActivity : AppCompatActivity(), ConstantesActivity {

    private val dao: CarroDAO by lazy {
        AppDatabase.getInstance(this).carroDAO()
    }
    private val listaContatoView: ListView by lazy { findViewById(R.id.activity_lista_contatos) }
    private val adapter: ListaCarrosAdapter by lazy {
        ListaCarrosAdapter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_carros)
        title = TITULO_CARROS
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
                    var carroEscolhido: Carro = adapter.getItem(menuInfo.position)
                    //Ponto de Exclamação duplo força um NullPointerException
                    dao.remover(carroEscolhido)
                    adapter.remove(carroEscolhido)
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
            startActivity(Intent(this@ListaCarrosActivity, FormularioContatoActivity::class.java))
        }
    }

    private fun configurarAdapter() {
        listaContatoView.adapter = adapter
    }

    private fun configurarListinerDeCliqueNoItem() {

        listaContatoView.setOnItemClickListener { parent, view, position, id ->
            val carroEscolhido: Carro = parent.getItemAtPosition(position) as Carro
            val formularioAlteracao = Intent(this, FormularioContatoActivity::class.java)
            formularioAlteracao.putExtra(CHAVE_EXTRA_CONTATO, carroEscolhido)
            startActivity(formularioAlteracao)
        }
    }
}


