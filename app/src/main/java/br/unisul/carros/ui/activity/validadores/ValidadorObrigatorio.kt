package br.unisul.carros.ui.activity.validadores

import com.google.android.material.textfield.TextInputLayout

class ValidadorObrigatorio(val textInputLayout: TextInputLayout): Validador {
    override fun invalido(): Boolean {
        val editText = textInputLayout.editText
        if (editText?.text.toString().isEmpty()) {
            textInputLayout.error = "Campo Obrigatório"
            textInputLayout.isErrorEnabled = true
            return true
        } else{
            textInputLayout.error = null
            textInputLayout.isErrorEnabled = false
        }

        return false
    }

    override fun removerFormatacao() {}
}