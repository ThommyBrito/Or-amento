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

    private val opcoesDianteira = arrayOf(
        "Amassado no para-choque",
        "Desalinhamento do capô",
        "Arranhões na pintura",
        "Retoque de pintura",
        "Desempenar capô",
        "Reparar lata com massa"
    )

    private val opcoesLateral = arrayOf(
        "Amassado na porta",
        "Arranhões na lateral",
        "Desalinhamento da lataria",
        "Retoque de pintura",
        "Desempenar lateral",
        "Refazer pintura completa da lateral"
    )

    private val opcoesTraseira = arrayOf(
        "Amassado no porta-malas",
        "Arranhões no para-choque traseiro",
        "Retoque de pintura",
        "Desempenar tampa traseira",
        "Alinhamento do porta-malas",
        "Reparo com massa plástica"
    )

    private val opcoesPolimento = arrayOf(
        "Remover riscos leves",
        "Remover manchas",
        "Descontaminação da pintura",
        "Polimento técnico",
        "Refinamento da pintura",
        "Aplicação de cera"
    )

    // ✅ Listas selecionadas
    private val selecionadosDianteira = mutableListOf<String>()
    private val selecionadosLateral = mutableListOf<String>()
    private val selecionadosTraseira = mutableListOf<String>()
    private val selecionadosPolimento = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_danificacoes)

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
