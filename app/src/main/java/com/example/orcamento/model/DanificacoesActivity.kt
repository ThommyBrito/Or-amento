package com.example.orcamento.model

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.orcamento.R
import org.json.JSONArray
import org.json.JSONObject

class DanificacoesActivity : AppCompatActivity() {

    private lateinit var inputNome: EditText
    private lateinit var inputTelefone: EditText
    private lateinit var inputModelo: EditText

    private lateinit var btnDianteira: LinearLayout
    private lateinit var btnLateral: LinearLayout
    private lateinit var btnTraseira: LinearLayout
    private lateinit var btnPolimento: LinearLayout
    private lateinit var btnFinalizar: Button

    private val opcoesDianteira = arrayOf(
        "Recuperar e Pintar para-choque",
        "Funilaria e Pintura no capô",
        "Funilaria e Pintura no para-lama esquerdo",
        "Funilaria e Pintura no para-lama direito",
        "Alinhamento"
    )

    private val opcoesLateral = arrayOf(
        "Funilaria e Pintura na porta do motorista",
        "Funilaria e Pintura na porta do passageiro",
        "Funilaria e Pintura na porta traseira esquerda",
        "Funilaria e Pintura na porta traseira direita",
        "Alinhamento"
    )

    private val opcoesTraseira = arrayOf(
        "Funilaria e Pintura na lateral esquerda",
        "Funilaria e Pintura na lateral direita",
        "Funilaria e Pintura na tampa traseira",
        "Recuperar e Pintar para-choque",
        "Alinhamento"
    )

    private val opcoesPolimento = arrayOf(
        "Polimento Geral simples",
        "Polimento Geral técnico",
        "Cristalização e Vitrificação"
    )

    private val selecionadosDianteira = mutableListOf<String>()
    private val selecionadosLateral = mutableListOf<String>()
    private val selecionadosTraseira = mutableListOf<String>()
    private val selecionadosPolimento = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_danificacoes)

        inputNome = findViewById(R.id.input_nome)
        inputTelefone = findViewById(R.id.input_telefone)
        inputModelo = findViewById(R.id.input_modelo)

        btnDianteira = findViewById(R.id.btn_dianteira)
        btnLateral = findViewById(R.id.btn_lateral)
        btnTraseira = findViewById(R.id.btn_traseira)
        btnPolimento = findViewById(R.id.btn_polimento)
        btnFinalizar = findViewById(R.id.btn_finalizar)

        btnDianteira.setOnClickListener {
            mostrarDialogMultiselect("Danos Dianteira", opcoesDianteira, selecionadosDianteira)
        }

        btnLateral.setOnClickListener {
            mostrarDialogMultiselect("Danos Lateral", opcoesLateral, selecionadosLateral)
        }

        btnTraseira.setOnClickListener {
            mostrarDialogMultiselect("Danos Traseira", opcoesTraseira, selecionadosTraseira)
        }

        btnPolimento.setOnClickListener {
            mostrarDialogMultiselect("Danos Polimento", opcoesPolimento, selecionadosPolimento)
        }

        btnFinalizar.setOnClickListener {
            enviarParaBackend()
        }
    }

    private fun mostrarDialogMultiselect(
        titulo: String,
        opcoes: Array<String>,
        selecionados: MutableList<String>
    ) {
        val selecionadosTemp = BooleanArray(opcoes.size) { selecionados.contains(opcoes[it]) }

        AlertDialog.Builder(this)
            .setTitle(titulo)
            .setMultiChoiceItems(opcoes, selecionadosTemp) { _, index, isChecked ->
                if (isChecked) {
                    if (!selecionados.contains(opcoes[index])) {
                        selecionados.add(opcoes[index])
                    }
                } else {
                    selecionados.remove(opcoes[index])
                }
            }
            .setPositiveButton("OK", null)
            .show()
    }

    private fun enviarParaBackend() {
        val nome = inputNome.text.toString().trim()
        val telefone = inputTelefone.text.toString().trim()
        val modelo = inputModelo.text.toString().trim()

        val json = JSONObject().apply {
            put("nome_cliente", nome)
            put("telefone_cliente", telefone)
            put("modelo_carro", modelo)
            put("dianteira", JSONArray(selecionadosDianteira))
            put("lateral", JSONArray(selecionadosLateral))
            put("traseira", JSONArray(selecionadosTraseira))
            put("polimento", JSONArray(selecionadosPolimento))
        }

        Log.d("ENVIANDO_JSON", json.toString())
        val intent = Intent(this, ResumoActivity::class.java)
        intent.putExtra("json_danificacoes", json.toString())
        startActivity(intent)
    }
}
