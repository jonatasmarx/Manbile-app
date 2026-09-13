package com.manbile.app;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;

public class LoginActivity extends AppCompatActivity {

    private EditText edtUsuario;
    private EditText edtSenha;
    private Button btnEntrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        edtUsuario = findViewById(R.id.edtUsuario);
        edtSenha = findViewById(R.id.edtSenha);
        btnEntrar = findViewById(R.id.btnEntrar);

        btnEntrar.setOnClickListener(v -> validarLogin());
    }

    private void validarLogin() {

        String usuario = edtUsuario.getText().toString().trim();
        String senha = edtSenha.getText().toString().trim();

        if (usuario.isEmpty()) {

            edtUsuario.setError("Informe o usuário");
            edtUsuario.requestFocus();
            return;

        }

        if (senha.isEmpty()) {

            edtSenha.setError("Informe a senha");
            edtSenha.requestFocus();
            return;

        }

        Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
        startActivity(intent);
        finish();
    }
}