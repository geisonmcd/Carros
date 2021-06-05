package br.unisul.carros.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import br.unisul.carros.R
import br.unisul.carros.dao.AppDatabase
import br.unisul.carros.dao.CarroDAO
import br.unisul.carros.dao.TelefoneDAO
import br.unisul.carros.model.Carro
import br.unisul.carros.model.Telefone
import br.unisul.carros.model.enuns.TipoFone
import br.unisul.carros.ui.activity.validadores.*
import com.google.android.material.textfield.TextInputLayout

var MENSAGEM_EMAIL_INVALIDO: String = ""

class FormularioContatoActivity : AppCompatActivity(), ConstantesActivity {

    private val carro = Carro()
    private var telefonesContato: MutableList<Telefone> = ArrayList()
    private lateinit var marcaModelo: TextInputLayout
    private lateinit var proprietario: TextInputLayout
    private lateinit var placa: TextInputLayout
    private lateinit var campoTelefoneResidencial: TextInputLayout
    private lateinit var campoTelefoneCelular: TextInputLayout
    private lateinit var campoEmail: TextInputLayout
    private val validadoresLista: MutableList<Validador> = ArrayList()
    private val carroDAO: CarroDAO by lazy {
        AppDatabase.getInstance(this).carroDAO()
    }
    private val telefoneDAO: TelefoneDAO by lazy {
        AppDatabase.getInstance(this).telefoneDAO()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario_carro)
        inicializacaoDosCampos()
        configurarEdicao()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_formulario_contato_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val itemId = item.itemId
        if (itemId == R.id.activity_formulario_contato_menu_salvar_botao) {
            finalizarFormulario()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun inicializacaoDosCampos() {
        configurarCampoMarcaModelo()
        configurarCampoProprietario()
        configurarCampoPlaca()
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

    private fun configurarCampoMarcaModelo() {
        marcaModelo = findViewById(R.id.activity_formulario_carro_layout_marca_modelo)
        val validador = ValidadorObrigatorio(marcaModelo)
        validadoresLista.add(validador)
        validarExibicaoObrigatorio(marcaModelo, validador)
    }

    private fun configurarCampoProprietario() {
        proprietario = findViewById(R.id.activity_formulario_carro_layout_proprietario)
        val validador = ValidadorObrigatorio(proprietario)
        validadoresLista.add(validador)
        validarExibicaoObrigatorio(proprietario, validador)
    }

    private fun configurarCampoPlaca() {
        placa = findViewById(R.id.activity_formulario_carro_layout_placa)
        val validador = ValidadorPlaca(placa)
        validadoresLista.add(validador)
        validarExibicaoObrigatorio(placa, validador)
    }

    private fun configurarCampoTelefoneResidencial() {
        campoTelefoneResidencial =
            findViewById(R.id.activity_formulario_contato_layout_fone_residencial)
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
            title = TITULO_FORMULARIO_CARROS
        }
    }

    private fun preencheDadosParaEditar(dados: Intent) {
        val contatoEdita = dados.getSerializableExtra(CHAVE_EXTRA_CONTATO) as Carro
        preencheCamposTela(contatoEdita)
        this.carro.id = contatoEdita.id
        this.carro.setCampos(contatoEdita.nome, contatoEdita.email, contatoEdita.proprietario, contatoEdita.placa)
        telefonesContato = telefoneDAO.buscaTodosTelefones(this.carro.id)
        for (telefone in telefonesContato) {
            if (telefone.tipo == TipoFone.RESIDENCIAL) {
                campoTelefoneResidencial.editText?.setText(telefone.numero)
            } else {
                campoTelefoneCelular.editText?.setText(telefone.numero)
            }
        }
    }

    private fun preencheCamposTela(carroTela: Carro) {
        marcaModelo.editText?.setText(carroTela.nome)
        campoEmail.editText?.setText(carroTela.email)
        proprietario.editText?.setText(carroTela.proprietario)
        placa.editText?.setText(carroTela.placa)
    }

    private fun finalizarFormulario() {
        var validaFormulario = true
        for (validador in validadoresLista) {
            if (validador.invalido()) {
                validaFormulario = false
            }
        }
        if (validaFormulario) {
            preencheContato()
            if (carro.temIdValido()) {
                carroDAO.edita(carro)
                val (telefoneResidencial, telefoneCelular) = vinculaTelefones(carro.id)
                telefoneDAO.atualiza(telefoneResidencial, telefoneCelular)
            } else {
                salvarContato()
            }
            finish()
        }
    }

    private fun vinculaTelefones(idContato: Long): Pair<Telefone, Telefone> {
        val telefoneResidencial = Telefone(
            campoTelefoneResidencial.editText?.text.toString(),
            TipoFone.RESIDENCIAL, idContato
        )
        val telefoneCelular = Telefone(
            campoTelefoneCelular.editText?.text.toString(),
            TipoFone.CELULAR, idContato
        )
        for (telefone in telefonesContato) {
            if (telefone.tipo == TipoFone.CELULAR) {
                telefoneCelular.id = telefone.id
            } else {
                telefoneResidencial.id = telefone.id
            }
        }
        return Pair(telefoneResidencial, telefoneCelular)
    }

    private fun salvarContato() {
        val idContato = carroDAO.salva(this.carro)
        val (foneResidencial, foneCelular) = vinculaTelefones(idContato)
        telefoneDAO.salvar(foneResidencial, foneCelular)
    }

    private fun preencheContato() {
        this.carro.setCampos(
            marcaModelo.editText?.text.toString(),
            campoEmail.editText?.text.toString(),
            proprietario.editText?.text.toString(),
            placa.editText?.text.toString()
        )
    }


}