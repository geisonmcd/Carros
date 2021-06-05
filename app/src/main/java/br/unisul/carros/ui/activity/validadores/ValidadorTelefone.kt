package br.unisul.carros.ui.activity.validadores

import com.google.android.material.textfield.TextInputLayout

class ValidadorTelefone(private val textInputLayout: TextInputLayout) : Validador {
    private val editText = textInputLayout.editText

    override fun estaInvalido(): Boolean {
        val validadorObrigatorio = ValidadorObrigatorio(textInputLayout)
        if (validadorObrigatorio.estaInvalido()) return true
        removerFormatacao()
        formatarTelefone()
        return false
    }

    private fun formatarTelefone() {
        val digitos = editText?.text.toString().length
        if ((digitos >= 10) && (digitos <= 11)) {
            val padraoFormatadoRegex = "([0-9]{2})([0-9]{4,5})([0-9]{4})".toRegex()
            val padraoMascara = "($1) $2-$3"
            var fone = editText?.text.toString().replace(padraoFormatadoRegex, padraoMascara)
            editText?.setText(fone)
            textInputLayout.error = null
            textInputLayout.isErrorEnabled = false
        } else {
            textInputLayout.error = "Não é telefone válido"
            textInputLayout.isErrorEnabled = true
        }
    }

    override fun removerFormatacao() {
        var fone = this.editText?.text.toString()
        fone = fone.replace("(", "")
            .replace(")", "")
            .replace(" ", "")
            .replace("-", "")
        editText?.setText(fone)
    }
}