package br.unisul.listacontatos.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import br.unisul.listacontatos.R
import br.unisul.listacontatos.dao.AppDatabase
import br.unisul.listacontatos.dao.ContatoDAO
import br.unisul.listacontatos.model.Contato

class FormularioContatoActivity : AppCompatActivity(), ConstantesActivity {
    private val contato = Contato()
    private lateinit var campoNome: EditText
    private lateinit var campoTelefone: EditText
    private lateinit var campoEmail: EditText
    private val dao: ContatoDAO by lazy {
        AppDatabase.getInstance(this).contadoDAO()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario_contato)
        inicializacaoDosCampos()
        configurarEdicao()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_formulario_contato_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val itemId = item.itemId
        if (itemId == R.id.activity_formulario_contato_menu_salvar_botao){
            finalizarFormulario()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun inicializacaoDosCampos() {
        configurarCampoNome()
        configurarCampoTelefone()
        configurarCampoEmail()
    }

    private fun configurarCampoEmail() {
        campoEmail = findViewById(R.id.activity_formulario_contato_email)
        campoEmail.onFocusChangeListener = object : View.OnFocusChangeListener{
            override fun onFocusChange(v: View?, hasFocus: Boolean) {
                if (!hasFocus) {
                    val email = campoEmail.text.toString()
                    Log.e("erro email", "email>"+email.matches(".+@.+\\..+".toRegex()))
                    if (!email.matches(".+@.+\\..+".toRegex())) {
                        campoEmail.error = "Email inválido"
                    }
                }
            }

        }
    }

    private fun configurarCampoTelefone() {
        campoTelefone = findViewById(R.id.activity_formulario_contato_fone)


        campoTelefone.onFocusChangeListener = object : View.OnFocusChangeListener {
            override fun onFocusChange(v: View?, hasFocus: Boolean) {
                if (!hasFocus){
                    if (campoTelefone.text.isEmpty()){
                        campoTelefone.error = "Campo Obrigatório"
                    } else{
                        var fone:String = campoTelefone.text.toString()
                        fone = fone.replace("(","")
                            .replace(")","")
                            .replace(" ","")
                            .replace("-","")
                            campoTelefone.setText(fone)
                        if ((fone.length>=10)&&(fone.length<=11)){
                            val padraoFormatadoRegex = "([0-9]{2})([0-9]{4,5})([0-9]{4})".toRegex()
                            val padraoMascara = "($1) $2-$3"
                            fone = campoTelefone.text.replace(padraoFormatadoRegex,padraoMascara)
                            Log.e("fone", "erro: "+fone)
                            campoTelefone.setText(fone)
                        } else{
                            campoTelefone.error = "Não é telefone válido"
                        }

                    }

                }
            }

        }
    }

    private fun configurarCampoNome() {
        campoNome = findViewById(R.id.activity_formulario_contato_nome)
        campoNome.onFocusChangeListener = object : View.OnFocusChangeListener{
            override fun onFocusChange(v: View?, hasFocus: Boolean) {
                if (!hasFocus) {
                    if (campoNome.text.isEmpty()) {
                        campoNome.error = "Campo Obrigatório"
                    }
                }
            }

        }

    }

    private fun configurarEdicao() {
        val dados: Intent = intent
        if (dados.hasExtra(CHAVE_EXTRA_CONTATO)) {
            title = TITULO_EDITA_FORMULARIO_CONTATOS
            preencheDadosParaEditar(dados)
        } else {
            title = TITULO_FORMULARIO_CONTATOS

        }
    }

    private fun preencheDadosParaEditar(dados: Intent) {
        val contatoEdita = dados.getSerializableExtra(CHAVE_EXTRA_CONTATO) as Contato
        preencheCamposTela(contatoEdita)
        this.contato.id = contatoEdita.id
        this.contato.setCampos(contatoEdita.nome, contatoEdita.telefone, contatoEdita.email)
    }

    private fun preencheCamposTela(contatoTela: Contato) {
        campoNome.setText(contatoTela.nome)
        campoTelefone.setText(contatoTela.telefone)
        campoEmail.setText(contatoTela.email)
    }

    private fun finalizarFormulario() {
        preencheContato()
        if (contato.temIdValido()) {
            dao.edita(contato)
        } else {
            salvarContato()
        }
        finish()
    }

    private fun salvarContato() {
        dao.salva(this.contato)
    }

     private fun preencheContato() {
        this.contato.setCampos(
            campoNome.text.toString(),
            campoTelefone.text.toString(),
            campoEmail.text.toString()
        )
    }


}