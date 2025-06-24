package com.example.orcamento.model

data class Orcamento(
    val nome_cliente: String,
    val telefone_cliente: String,
    val modelo_carro: String,
    val dianteira: List<String>,
    val lateral_dano: List<String>, // nome alterado aqui também
    val traseira: List<String>,
    val polimento: List<String>
)