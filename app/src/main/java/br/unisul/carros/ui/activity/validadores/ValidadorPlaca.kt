package br.unisul.carros.ui.activity.validadores

import br.unisul.carros.ui.activity.MENSAGEM_EMAIL_INVALIDO
import com.google.android.material.textfield.TextInputLayout

class ValidadorPlaca(private val textInputLayout: TextInputLayout) : Validador {

    private val editText = textInputLayout.editText

    override fun estaInvalido(): Boolean {
        val validadorObrigatorio = ValidadorObrigatorio(textInputLayout)
        if (validadorObrigatorio.estaInvalido()) return true
        removerFormatacao()
        return formatarPlaca()
        return false
    }

    private fun formatarPlaca(): Boolean {
        val length = editText?.text.toString().length
        if (length == 7) {
            val padraoFormatadoRegex = "^([a-zA-Z]{3})([0-9]{4})$".toRegex()
            val padraoMascara = "$1-$2"
            if (!editText?.text.toString().matches(padraoFormatadoRegex)) {
                textInputLayout.error = "A placa não bate com o padrão XXX-YYYY"
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