package com.proway.testapp.utils

data class Conta(
    val nome: String,
    val numero1: Int,
    val numero2: Int,
    val operacao: Operacao
)

data class Operacao(

    val nome: String,
    val op: ETipoOperacao
)

enum class ETipoOperacao {
    SOMAR,
    SUBTRAIR
}
