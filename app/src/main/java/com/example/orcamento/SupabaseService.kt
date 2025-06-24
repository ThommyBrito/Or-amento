package com.example.orcamento.api

import com.example.orcamento.BaseSupabaseApi
import com.example.orcamento.model.Orcamento
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface SupabaseService {

    @Headers(
        "Content-Type: application/json",
        "apikey: ${BaseSupabaseApi.API_KEY}",
        "Authorization: Bearer ${BaseSupabaseApi.API_KEY}"
    )
    @POST("orcamentos") // nome da tabela
    fun enviarOrcamento(@Body orcamento: Orcamento): Call<Void>
}
