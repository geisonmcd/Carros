package br.unisul.listacontatos.ui.activity.validadores

interface Validador {
    fun invalido(): Boolean
    fun removerFormatacao()
}