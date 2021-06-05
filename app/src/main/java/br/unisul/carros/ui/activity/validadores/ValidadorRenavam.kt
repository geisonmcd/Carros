package br.unisul.carros.ui.activity.validadores

import br.unisul.carros.ui.activity.MENSAGEM_EMAIL_INVALIDO
import com.google.android.material.textfield.TextInputLayout

class ValidadorRenavam(private val textInputLayout: TextInputLayout) : Validador {

    private val editText = textInputLayout.editText

    override fun estaInvalido(): Boolean {
        val validadorObrigatorio = ValidadorObrigatorio(textInputLayout)
        if (validadorObrigatorio.estaInvalido()) return true
        removerFormatacao()
        return formatarRenavam()
        return false
    }

    private fun formatarRenavam(): Boolean {
        val length = editText?.text.toString().length
        if (length == 11) {
            val padraoFormatadoRegex = "^([0-9]{10})([0-9]{1})$".toRegex()
            val padraoMascara = "$1-$2"
            if (!editText?.text.toString().matches(padraoFormatadoRegex)) {
                textInputLayout.error = MENSAGEM_EMAIL_INVALIDO
                textInputLayout.isErrorEnabled = true
                return true;
            }
            var placa = editText?.text.toString().replace(padraoFormatadoRegex, padraoMascara)
            editText?.setText(placa)
            textInputLayout.error = null
            textInputLayout.isErrorEnabled = false
        } else {
            textInputLayout.error = "Não é uma placa válida"
            textInputLayout.isErrorEnabled = true
            return true
        }
        return false
    }

    override fun removerFormatacao() {
        var placa = this.editText?.text.toString()
        placa = placa.replace("-", "")
        editText?.setText(placa)
    }
}