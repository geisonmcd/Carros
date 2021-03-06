package br.unisul.carros.ui.activity.validadores

import br.unisul.carros.ui.activity.MENSAGEM_EMAIL_INVALIDO
import com.google.android.material.textfield.TextInputLayout

class ValidadorEmail(private val textInputLayout: TextInputLayout) : Validador {
    val editText = textInputLayout.editText
    override fun estaInvalido(): Boolean {

        val validadorObrigatorio = ValidadorObrigatorio(textInputLayout)
        if (validadorObrigatorio.estaInvalido()) return true

        return formatarEmail()
    }

    private fun formatarEmail(): Boolean {
        val email = editText?.text.toString()
        if (!email.matches(".+@.+\\..+".toRegex())) {
            textInputLayout.error = MENSAGEM_EMAIL_INVALIDO
            textInputLayout.isErrorEnabled = true
            return true
        }
        return false
    }

    override fun removerFormatacao() {}

}
