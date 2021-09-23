package com.proway.testapp.utils

import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class CalculadoraTest{

    @Mock
    lateinit var conta: Conta
    @Mock
    lateinit var operacao: Operacao

    @Before
    fun setUp(){
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `test calculadora`() {
        Mockito.`when`(conta.numero1)
            .thenReturn(2)
        Mockito.`when`(conta.numero2)
            .thenReturn(10)
        Mockito.`when`(conta.operacao)
            .thenReturn(operacao)
        Mockito.`when`(operacao.op)
            .thenReturn(ETipoOperacao.SOMAR)

        val result = Calculadora().calc(conta)
        assertThat(result).isEqualTo(12)
    }

}