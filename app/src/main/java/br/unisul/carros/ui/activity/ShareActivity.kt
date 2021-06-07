package br.unisul.carros.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import br.unisul.carros.R
import br.unisul.carros.dao.AppDatabase
import br.unisul.carros.dao.CarroDAO
import br.unisul.carros.model.Carro
import com.google.android.material.textfield.TextInputLayout

class ShareActivity : AppCompatActivity(), ConstantesActivity, AdapterView.OnItemSelectedListener {

    private lateinit var queryString: TextInputLayout
    private lateinit var campo: String
    private var spinnerPosition: Int = 0
    private val carroDAO: CarroDAO by lazy {
        AppDatabase.getInstance(this).carroDAO()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share)
        inicializacaoDosCampos()
        title = "Compartilhar Carros"
        configuraSpinner()
    }

    private fun configuraSpinner() {
        val spinner: Spinner = findViewById(R.id.mySpinner)
        ArrayAdapter.createFromResource(
            this,
            R.array.campos_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
        spinner.onItemSelectedListener = this
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_formulario_carro_share, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val itemId = item.itemId
        if (itemId == R.id.activity_formulario_carro_menu_share_botao) {
            shareData()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun inicializacaoDosCampos() {
        queryString = findViewById(R.id.activity_share_layout_query)
    }

    private fun shareData() {
        val query: String =  queryString.editText?.text.toString()
        var carros: List<Carro> = arrayListOf<Carro>()
        when (spinnerPosition) {
            0 -> carros = carroDAO.buscaCarroByMarcaModelo(query)
            1 -> carros = carroDAO.buscaCarroByProprietario(query)
        }
        openShareFunction(carros)
    }

    private fun openShareFunction(carros: List<Carro>) {
        val intent = Intent()
        intent.action = Intent.ACTION_SEND
        intent.putExtra(Intent.EXTRA_TEXT, getTextThatWillBeShared(carros))
        intent.type = "text/plain"
        startActivity(Intent.createChooser(intent, "Compartilhe com:"))
    }

    private fun getTextThatWillBeShared(carros: List<Carro>): String {
        var text = "MARCA/MODELO PLACA PROPRIETARIO\n"
        for (carro in carros) {
            text += carro.toString()
        }
        println("==========================================")
        println(text)
        println("==========================================")
        return text
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, id: Long) {
        this.spinnerPosition = position
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        println("NOTHING")
    }
}