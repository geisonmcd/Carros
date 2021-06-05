package br.unisul.carros.ui.activity.validadores

import com.google.android.material.textfield.TextInputLayout

class ValidadorPlaca(private val textInputLayout: TextInputLayout) : Validador {

    private val editText = textInputLayout.editText

    override fun invalido(): Boolean {
        val validadorObrigatorio = ValidadorObrigatorio(textInputLayout)
        if (validadorObrigatorio.invalido()) return true
        removerFormatacao()
        formatarPlaca()
        return false
    }

    private fun formatarPlaca() {
        val length = editText?.text.toString().length
        if (length == 7) {
            val padraoFormatadoRegex = "([a-zA-Z]{3})([0-9]{3})".toRegex()
            val padraoMascara = "$1-$2"
            var placa = editText?.text.toString().replace(padraoFormatadoRegex, padraoMascara)
            editText?.setText(placa)
            textInputLayout.error = null
            textInputLayout.isErrorEnabled = false
        } else {
            textInputLayout.error = "Não é uma placa válida"
            textInputLayout.isErrorEnabled = true
        }
    }

    override fun removerFormatacao() {
        var placa = this.editText?.text.toString()
        placa = placa.replace("-", "")
        editText?.setText(placa)
    }
}