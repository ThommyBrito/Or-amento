package com.example.orcamento

import android.os.Bundle

import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.orcamento.R
import android.content.Intent
import com.example.orcamento.model.DanificacoesActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnOrcamento = findViewById<Button>(R.id.btnOrcamento)

        btnOrcamento.setOnClickListener {
            val intent = Intent(this, DanificacoesActivity::class.java)
            startActivity(intent)
        }
    }
}