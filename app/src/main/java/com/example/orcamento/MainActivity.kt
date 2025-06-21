package com.seuprojeto.orcamento

import android.os.Bundle

import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.orcamento.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnOrcamento = findViewById<Button>(R.id.btnOrcamento)

        btnOrcamento.setOnClickListener {
            Log.d("MainActivity", "Botão Orçamento clicado")
        }
    }
}
