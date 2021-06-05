package br.unisul.carros.ui.activity

interface ConstantesActivity {
    val MSG_CONFIRMACAO_EXCLUSAO : String
        get() = "Cuidado, os dados serão perdidos se continuar. Deseja continuar?"
    val TITULO_CONFIRMACAO_EXCLUSAO : String
        get() = "Exclusão do Carro"
    val ERRO_NULL_POITER: String
        get() = "Erro na selação do menu"

    val NOME_BOTAO_SIM
        get() = "Sim"
    val NOME_BOTAO_NAO
        get() = "Não"

    val TITULO_CARROS: String
        get() = "Lista de Carros"
    val TITULO_FORMULARIO_CARROS: String
        get() = "Cadastro de Carros"
    val TITULO_EDITA_FORMULARIO_CONTATOS: String
        get() = "Editar Carro"
    val CHAVE_EXTRA_CONTATO: String
    get() = "contato"
}