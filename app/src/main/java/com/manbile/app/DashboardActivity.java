package com.manbile.app;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity {

    private Button btnMinhasAtividades;
    private Button btnNovaAtividade;
    private Button btnHistorico;
    private Button btnRelatorios;
    private Button btnPerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dashboard);

        btnMinhasAtividades = findViewById(R.id.btnMinhasAtividades);
        btnNovaAtividade = findViewById(R.id.btnNovaAtividade);
        btnHistorico = findViewById(R.id.btnHistorico);
        btnRelatorios = findViewById(R.id.btnRelatorios);
        btnPerfil = findViewById(R.id.btnPerfil);

        btnMinhasAtividades.setOnClickListener(v -> {

            Intent intent =
                    new Intent(DashboardActivity.this, AtividadesActivity.class);

            startActivity(intent);
        });

        btnNovaAtividade.setOnClickListener(v ->
                Toast.makeText(this, "Nova Atividade", Toast.LENGTH_SHORT).show()
        );

        btnHistorico.setOnClickListener(v ->
                Toast.makeText(this, "Histórico", Toast.LENGTH_SHORT).show()
        );

        btnRelatorios.setOnClickListener(v ->
                Toast.makeText(this, "Relatórios", Toast.LENGTH_SHORT).show()
        );

        btnPerfil.setOnClickListener(v ->
                Toast.makeText(this, "Perfil", Toast.LENGTH_SHORT).show()
        );
    }
}