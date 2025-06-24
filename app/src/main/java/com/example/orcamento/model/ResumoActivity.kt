package com.example.orcamento.model

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.orcamento.MainActivity
import com.example.orcamento.R
import org.json.JSONObject

class ResumoActivity : AppCompatActivity() {

    private lateinit var txtDescricaoItens: TextView
    private lateinit var txtValor: TextView
    private lateinit var btnSalvar: Button
    private lateinit var btnVoltar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resumo)


        txtDescricaoItens = findViewById(R.id.txt_descricao_itens)
        txtValor = findViewById(R.id.txt_valor)
        btnSalvar = findViewById(R.id.btn_salvar)
        btnVoltar = findViewById(R.id.btn_voltar)

        val jsonString = intent.getStringExtra("json_danificacoes")
        if (jsonString != null) {
            processarJson(jsonString)
        }

        btnSalvar.setOnClickListener {
            val nomeCliente = if (jsonString != null) {
                val json = JSONObject(jsonString)
                json.optString("nome_cliente", "cliente")
            } else {
                "cliente"
            }

            val mensagem = "Estamos felizes em atendê-lo(a), $nomeCliente. Logo entraremos em contato pelo WhatsApp."

            val dialog = AlertDialog.Builder(this)
                .setTitle("Obrigado!")
                .setMessage(mensagem)
                .setPositiveButton("OK") { _, _ ->
                    voltarParaMain()
                }
                .setOnDismissListener {
                    voltarParaMain()
                }
                .create()

            dialog.show()
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

        // Mostra os itens na tela
        txtDescricaoItens.text = todasAvarias.joinToString("\n")

        // Calcula o valor total (1.000 por item)
        val valorTotal = todasAvarias.size * 1000
        txtValor.text = "Valor: R$ ${String.format("%,d", valorTotal).replace(",", ".")},00"
    }

}
