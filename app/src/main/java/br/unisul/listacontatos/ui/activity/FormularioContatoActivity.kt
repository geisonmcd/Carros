package br.unisul.listacontatos.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import br.unisul.listacontatos.R
import br.unisul.listacontatos.dao.AppDatabase
import br.unisul.listacontatos.dao.ContatoDAO
import br.unisul.listacontatos.model.Contato
import br.unisul.listacontatos.ui.activity.validadores.Validador
import br.unisul.listacontatos.ui.activity.validadores.ValidadorEmail
import br.unisul.listacontatos.ui.activity.validadores.ValidadorObrigatorio
import br.unisul.listacontatos.ui.activity.validadores.ValidadorTelefone
import com.google.android.material.textfield.TextInputLayout

var MENSAGEM_EMAIL_INVALIDO: String = ""

class FormularioContatoActivity : AppCompatActivity(), ConstantesActivity {
    private val contato = Contato()
    private lateinit var campoNome: TextInputLayout
    private lateinit var campoTelefoneResidencial: TextInputLayout
    private lateinit var campoTelefoneCelular: TextInputLayout
    private lateinit var campoEmail: TextInputLayout
    private val validadoresLista:MutableList<Validador> = ArrayList()
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
        configurarCampoTelefoneResidencial()
        configurarCampoTelefoneCelular()
        configurarCampoEmail()
        MENSAGEM_EMAIL_INVALIDO = getString(R.string.MENSAGEM_EMAIL_INVALIDO)
    }
    private fun validarExibicaoObrigatorio(textInputLayout: TextInputLayout, validador: Validador) {
        val editText = textInputLayout.editText
        editText?.onFocusChangeListener = object : View.OnFocusChangeListener {
            override fun onFocusChange(v: View?, hasFocus: Boolean) {
                if (!hasFocus) {
                    if (validador.invalido()) return
                } else {
                    validador.removerFormatacao()
                }
            }

        }
    }

    private fun configurarCampoNome() {
        campoNome = findViewById(R.id.activity_formulario_contato_layout_nome)
        val validador= ValidadorObrigatorio(campoNome)
        validadoresLista.add(validador)
        validarExibicaoObrigatorio(campoNome, validador)

    }



    private fun configurarCampoTelefoneResidencial() {
        campoTelefoneResidencial = findViewById(R.id.activity_formulario_contato_layout_fone_residencial)
        val validador = ValidadorTelefone(campoTelefoneResidencial)
        validadoresLista.add(validador)
        validarExibicaoObrigatorio(campoTelefoneResidencial, validador)
    }
    private fun configurarCampoTelefoneCelular() {
        campoTelefoneCelular = findViewById(R.id.activity_formulario_contato_layout_celular)
        val validador = ValidadorTelefone(campoTelefoneCelular)
        validadoresLista.add(validador)
        validarExibicaoObrigatorio(campoTelefoneCelular, validador)
    }


    private fun configurarCampoEmail() {
        campoEmail = findViewById(R.id.activity_formulario_contato_layout_email)
        val validador = ValidadorEmail(campoEmail)
        validadoresLista.add(validador)
        validarExibicaoObrigatorio(campoEmail, validador)

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
//        this.contato.setCampos(contatoEdita.nome, contatoEdita.telefone, contatoEdita.email)
    }

    private fun preencheCamposTela(contatoTela: Contato) {
        campoNome.editText?.setText(contatoTela.nome)
//        campoTelefoneResidencial.editText?.setText(contatoTela.telefone)
//        campoTelefoneCelular.editText?.setText(contatoTela.telefone)
        campoEmail.editText?.setText(contatoTela.email)
    }

    private fun finalizarFormulario() {
        var validaFormulario = true
        for (validador in validadoresLista){
            if (validador.invalido()){
                validaFormulario=false
            }
        }
        if (validaFormulario) {
            preencheContato()
            if (contato.temIdValido()) {
                dao.edita(contato)
            } else {
                salvarContato()
            }
            finish()
        }
    }

    private fun salvarContato() {
        dao.salva(this.contato)
    }

     private fun preencheContato() {
        this.contato.setCampos(
            campoNome.editText?.text.toString(),
            campoEmail.editText?.text.toString()
        )
    }


}