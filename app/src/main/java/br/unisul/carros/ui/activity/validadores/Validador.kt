package br.unisul.carros.ui.activity.validadores

interface Validador {
    fun invalido(): Boolean
    fun removerFormatacao()
}