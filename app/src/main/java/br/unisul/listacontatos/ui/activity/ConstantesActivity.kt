package br.unisul.listacontatos.ui.activity

interface ConstantesActivity {
    val MSG_CONFIRMACAO_EXCLUSAO : String
        get() = "Cuidado, os dados serão perdidos se continuar. Deseja continuar?"
    val TITULO_CONFIRMACAO_EXCLUSAO : String
        get() = "Exclusão do Contato"
    val ERRO_NULL_POITER: String
        get() = "Erro na selação do menu"

    val NOME_BOTAO_SIM
        get() = "Sim"
    val NOME_BOTAO_NAO
        get() = "Não"

    val TITULO_LISTA_CONTATOS: String
        get() = "Lista de Contatos"
    val TITULO_FORMULARIO_CONTATOS: String
        get() = "Cadastro de Contatos"
    val TITULO_EDITA_FORMULARIO_CONTATOS: String
        get() = "Editar Contato"
    val CHAVE_EXTRA_CONTATO: String
    get() = "contato"
}