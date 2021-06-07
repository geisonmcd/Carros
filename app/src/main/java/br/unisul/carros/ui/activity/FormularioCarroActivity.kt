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
import br.unisul.carros.model.Carro
import br.unisul.carros.ui.activity.validadores.*
import com.google.android.material.textfield.TextInputLayout

var MENSAGEM_EMAIL_INVALIDO: String = ""

class FormularioCarroActivity : AppCompatActivity(), ConstantesActivity {

    private val carro = Carro()
    private lateinit var marcaModelo: TextInputLayout
    private lateinit var proprietario: TextInputLayout
    private lateinit var placa: TextInputLayout
    private lateinit var renavam: TextInputLayout
    private lateinit var telefone: TextInputLayout
    private lateinit var campoEmail: TextInputLayout
    private val validadoresLista: MutableList<Validador> = ArrayList()
    private val carroDAO: CarroDAO by lazy {
        AppDatabase.getInstance(this).carroDAO()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario_carro)
        inicializacaoDosCampos()
        configurarEdicao()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_formulario_carro_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val itemId = item.itemId
        if (itemId == R.id.activity_formulario_carro_menu_salvar_botao) {
            finalizarFormulario()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun inicializacaoDosCampos() {
        configurarCampoMarcaModelo()
        configurarCampoProprietario()
        configurarCampoPlaca()
        configurarCampoRenavam()
        configurarCampoTelefone()
        configurarCampoEmail()
        MENSAGEM_EMAIL_INVALIDO = getString(R.string.MENSAGEM_EMAIL_INVALIDO)
    }

    private fun validarExibicaoObrigatorio(textInputLayout: TextInputLayout, validador: Validador) {
        val editText = textInputLayout.editText
        editText?.onFocusChangeListener = object : View.OnFocusChangeListener {
            override fun onFocusChange(v: View?, hasFocus: Boolean) {
                if (!hasFocus) {
                    if (validador.estaInvalido()) return
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

    private fun configurarCampoRenavam() {
        renavam = findViewById(R.id.activity_formulario_carro_layout_renavam)
        val validador = ValidadorRenavam(renavam)
        validadoresLista.add(validador)
        validarExibicaoObrigatorio(renavam, validador)
    }

    private fun configurarCampoTelefone() {
        telefone = findViewById(R.id.activity_formulario_carro_layout_fone_residencial)
        val validador = ValidadorTelefone(telefone)
        validadoresLista.add(validador)
        validarExibicaoObrigatorio(telefone, validador)
    }

    private fun configurarCampoEmail() {
        campoEmail = findViewById(R.id.activity_formulario_carro_layout_email)
        val validador = ValidadorEmail(campoEmail)
        validadoresLista.add(validador)
        validarExibicaoObrigatorio(campoEmail, validador)
    }

    private fun configurarEdicao() {
        val dados: Intent = intent
        if (dados.hasExtra(CHAVE_EXTRA_CARRO)) {
            title = TITULO_EDITA_FORMULARIO_CARROS
            preencheDadosParaEditar(dados)
        } else {
            title = TITULO_FORMULARIO_CARROS
        }
    }

    private fun preencheDadosParaEditar(dados: Intent) {
        val carroEdita = dados.getSerializableExtra(CHAVE_EXTRA_CARRO) as Carro
        preencheCamposTela(carroEdita)
        this.carro.id = carroEdita.id
        this.carro.setCampos(carroEdita.marcaModelo, carroEdita.email, carroEdita.proprietario, carroEdita.placa, carroEdita.renavam, carroEdita.telefone)
    }

    private fun preencheCamposTela(carroTela: Carro) {
        marcaModelo.editText?.setText(carroTela.marcaModelo)
        campoEmail.editText?.setText(carroTela.email)
        proprietario.editText?.setText(carroTela.proprietario)
        placa.editText?.setText(carroTela.placa)
        renavam.editText?.setText(carroTela.renavam)
        telefone.editText?.setText(carroTela.telefone)
    }

    private fun finalizarFormulario() {
        var validaFormulario = true
        for (validador in validadoresLista) {
            println(validador.estaInvalido())
            if (validador.estaInvalido()) {
                validaFormulario = false
            }
        }
        if (validaFormulario) {
            preencheCarro()
            if (carro.temIdValido()) {
                carroDAO.edita(carro)
            } else {
                salvarCarro()
            }
            finish()
        }
    }

    private fun salvarCarro() {
        println(this.carro)
        carroDAO.salva(this.carro)
    }

    private fun preencheCarro() {
        this.carro.setCampos(
            marcaModelo.editText?.text.toString(),
            campoEmail.editText?.text.toString(),
            proprietario.editText?.text.toString(),
            placa.editText?.text.toString(),
            renavam.editText?.text.toString(),
            telefone.editText?.text.toString(),
        )
    }


}