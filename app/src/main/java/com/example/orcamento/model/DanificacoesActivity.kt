package com.example.orcamento.model

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.orcamento.R
import org.json.JSONArray
import org.json.JSONObject

class DanificacoesActivity : AppCompatActivity() {

    private lateinit var btnDianteira: LinearLayout
    private lateinit var btnLateral: LinearLayout
    private lateinit var btnTraseira: LinearLayout
    private lateinit var btnPolimento: LinearLayout
    private lateinit var btnFinalizar: Button

    private val opcoesDanos = arrayOf("Arranhão", "Amassado", "Trincado", "Faltando peça")

    private val selecionadosDianteira = mutableListOf<String>()
    private val selecionadosLateral = mutableListOf<String>()
    private val selecionadosTraseira = mutableListOf<String>()
    private val selecionadosPolimento = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_danificacoes)

        // Vincula os elementos do layout
        btnDianteira = findViewById(R.id.btn_dianteira)
        btnLateral = findViewById(R.id.btn_lateral)
        btnTraseira = findViewById(R.id.btn_traseira)
        btnPolimento = findViewById(R.id.btn_polimento)
        btnFinalizar = findViewById(R.id.btn_finalizar)

        // Clique em cada "botão"
        btnDianteira.setOnClickListener {
            mostrarDialogMultiselect("Danos Dianteira", opcoesDanos, selecionadosDianteira)
        }

        btnLateral.setOnClickListener {
            mostrarDialogMultiselect("Danos Lateral", opcoesDanos, selecionadosLateral)
        }

        btnTraseira.setOnClickListener {
            mostrarDialogMultiselect("Danos Traseira", opcoesDanos, selecionadosTraseira)
        }

        btnPolimento.setOnClickListener {
            mostrarDialogMultiselect("Danos Polimento", opcoesDanos, selecionadosPolimento)
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
        val json = JSONObject().apply {
            put("dianteira", JSONArray(selecionadosDianteira))
            put("lateral", JSONArray(selecionadosLateral))
            put("traseira", JSONArray(selecionadosTraseira))
            put("polimento", JSONArray(selecionadosPolimento))
        }

        Log.d("ENVIANDO_JSON", json.toString())
        // Aqui você pode usar Retrofit, Volley, etc.
    }
}
