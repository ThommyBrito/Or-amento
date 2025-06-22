package com.example.orcamento.model

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.orcamento.MainActivity
import com.example.orcamento.R
import com.example.orcamento.RetrofitClient
import com.example.orcamento.api.SupabaseService
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResumoActivity : AppCompatActivity() {

    private lateinit var txtDescricaoItens: TextView
    private lateinit var txtValor: TextView
    private lateinit var btnSalvar: Button
    private lateinit var btnVoltar: Button
    private var jsonString: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resumo)

        txtDescricaoItens = findViewById(R.id.txt_descricao_itens)
        txtValor = findViewById(R.id.txt_valor)
        btnSalvar = findViewById(R.id.btn_salvar)
        btnVoltar = findViewById(R.id.btn_voltar)

        jsonString = intent.getStringExtra("json_danificacoes")
        if (jsonString != null) {
            processarJson(jsonString!!)
        }

        btnSalvar.setOnClickListener {
            if (jsonString != null) {
                try {
                    val json = JSONObject(jsonString!!)
                    val orcamento = Orcamento(
                        nome_cliente = json.optString("nome_cliente", "cliente"),
                        telefone_cliente = json.optString("telefone_cliente", ""),
                        modelo_carro = json.optString("modelo_carro", ""),
                        dianteira = jsonArrayToList(json.optJSONArray("dianteira")),
                        lateral_dano = jsonArrayToList(json.optJSONArray("lateral")),
                        traseira = jsonArrayToList(json.optJSONArray("traseira")),
                        polimento = jsonArrayToList(json.optJSONArray("polimento"))
                    )

                    val service = RetrofitClient.retrofit.create(SupabaseService::class.java)
                    service.enviarOrcamento(orcamento).enqueue(object : Callback<Void> {
                        override fun onResponse(call: Call<Void>, response: Response<Void>) {
                            if (response.isSuccessful) {
                                mostrarDialogo(orcamento.nome_cliente)
                            } else {
                                Log.e("Supabase", "Erro: ${response.code()}")
                                Toast.makeText(this@ResumoActivity, "Erro ao salvar", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onFailure(call: Call<Void>, t: Throwable) {
                            Log.e("Supabase", "Falha: ${t.message}")
                            Toast.makeText(this@ResumoActivity, "Falha na conexão", Toast.LENGTH_SHORT).show()
                        }
                    })

                } catch (e: Exception) {
                    Log.e("Erro", "Falha ao processar JSON: ${e.message}")
                    Toast.makeText(this, "Erro ao processar dados", Toast.LENGTH_SHORT).show()
                }
            }
        }

        btnVoltar.setOnClickListener {
            finish()
        }
    }

    private fun voltarParaMain() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }

    private fun mostrarDialogo(nomeCliente: String) {
        val mensagem = "Estamos felizes em atendê-lo(a), $nomeCliente. Logo entraremos em contato pelo WhatsApp."

        val dialog = AlertDialog.Builder(this)
            .setTitle("Obrigado!")
            .setMessage(mensagem)
            .setPositiveButton("OK") { _, _ -> voltarParaMain() }
            .setOnDismissListener { voltarParaMain() }
            .create()

        dialog.show()
    }

    private fun processarJson(jsonString: String) {
        val json = JSONObject(jsonString)

        val categorias = mapOf(
            "dianteira" to "Dianteira",
            "lateral" to "Lateral",
            "traseira" to "Traseira",
            "polimento" to "Polimento"
        )

        val todasAvarias = mutableListOf<String>()

        for ((chave, nomeCategoria) in categorias) {
            val itensArray = json.optJSONArray(chave)
            if (itensArray != null) {
                for (i in 0 until itensArray.length()) {
                    val item = itensArray.getString(i)
                    todasAvarias.add("• $item ($nomeCategoria)")
                }
            }
        }

        txtDescricaoItens.text = todasAvarias.joinToString("\n")

        val valorTotal = todasAvarias.size * 1000
        txtValor.text = "Valor: R$ ${String.format("%,d", valorTotal).replace(",", ".")},00"
    }

    private fun jsonArrayToList(jsonArray: JSONArray?): List<String> {
        val list = mutableListOf<String>()
        if (jsonArray != null) {
            for (i in 0 until jsonArray.length()) {
                list.add(jsonArray.optString(i))
            }
        }
        return list
    }
}
