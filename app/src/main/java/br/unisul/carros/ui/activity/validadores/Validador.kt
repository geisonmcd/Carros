package br.unisul.carros.ui.activity.validadores

interface Validador {
    fun estaInvalido(): Boolean
    fun removerFormatacao()
}