package com.manbile.app;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.TextView;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;

public class DetalhesAtividadeActivity extends AppCompatActivity {

    private TextView txtTituloDetalhes;
    private TextView txtCliente;
    private TextView txtTipo;
    private TextView txtPrioridade;
    private TextView txtStatus;

    private EditText edtDiagnostico;
    private EditText edtSolucao;
    private EditText edtMateriais;
    private EditText edtObservacoes;

    private Button btnSalvarAtividade;
    private Button btnFinalizarAtividade;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detalhes_atividade);

        EditText edtCep = findViewById(R.id.edtCep);
        Button btnConsultarCep = findViewById(R.id.btnConsultarCep);
        TextView txtEndereco = findViewById(R.id.txtEndereco);
        txtTituloDetalhes = findViewById(R.id.txtTituloDetalhes);
        txtCliente = findViewById(R.id.txtCliente);
        txtTipo = findViewById(R.id.txtTipo);
        txtPrioridade = findViewById(R.id.txtPrioridade);
        txtStatus = findViewById(R.id.txtStatus);

        edtDiagnostico = findViewById(R.id.edtDiagnostico);
        edtSolucao = findViewById(R.id.edtSolucao);
        edtMateriais = findViewById(R.id.edtMateriais);
        edtObservacoes = findViewById(R.id.edtObservacoes);

        btnSalvarAtividade = findViewById(R.id.btnSalvarAtividade);
        btnFinalizarAtividade = findViewById(R.id.btnFinalizarAtividade);
        btnConsultarCep.setOnClickListener(v -> {

            String cep = edtCep.getText().toString().trim();

            if (cep.length() != 8) {
                Toast.makeText(
                        DetalhesAtividadeActivity.this,
                        "Digite um CEP válido com 8 números",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            ViaCepApi api = RetrofitClient
                    .getRetrofit()
                    .create(ViaCepApi.class);

            api.buscarEndereco(cep).enqueue(new retrofit2.Callback<Endereco>() {

                @Override
                public void onResponse(
                        retrofit2.Call<Endereco> call,
                        retrofit2.Response<Endereco> response) {

                    if (response.isSuccessful() && response.body() != null) {

                        Endereco endereco = response.body();

                        if (Boolean.TRUE.equals(endereco.getErro())) {

                            txtEndereco.setText("Endereço não consultado");

                            Toast.makeText(
                                    DetalhesAtividadeActivity.this,
                                    "CEP não encontrado",
                                    Toast.LENGTH_SHORT
                            ).show();

                            return;
                        }

                        String logradouro = endereco.getLogradouro() != null
                                ? endereco.getLogradouro()
                                : "";

                        String bairro = endereco.getBairro() != null
                                ? endereco.getBairro()
                                : "";

                        String localidade = endereco.getLocalidade() != null
                                ? endereco.getLocalidade()
                                : "";

                        String uf = endereco.getUf() != null
                                ? endereco.getUf()
                                : "";

                        String enderecoCompleto =
                                logradouro + "\n" +
                                        bairro + "\n" +
                                        localidade + " - " + uf;

                        txtEndereco.setText(enderecoCompleto);

                    } else {

                        Toast.makeText(
                                DetalhesAtividadeActivity.this,
                                "Erro ao consultar CEP",
                                Toast.LENGTH_SHORT
                        ).show();
                    }

                }

                @Override
                public void onFailure(
                        retrofit2.Call<Endereco> call,
                        Throwable t) {

                    Toast.makeText(
                            DetalhesAtividadeActivity.this,
                            "Erro na consulta: " + t.getMessage(),
                            Toast.LENGTH_LONG
                    ).show();
                }
            });
        });

        int id = getIntent().getIntExtra("id", 0);

        String cliente = getIntent().getStringExtra("cliente");
        String tipo = getIntent().getStringExtra("tipo");
        String prioridade = getIntent().getStringExtra("prioridade");
        String status = getIntent().getStringExtra("status");

        SharedPreferences preferences =
                getSharedPreferences("atividades", MODE_PRIVATE);

        String statusAtual =
                preferences.getString(
                        "status_" + id,
                        status
                );

        txtTituloDetalhes.setText("OS #" + id);
        txtCliente.setText("Cliente: " + cliente);
        txtTipo.setText("Tipo: " + tipo);
        txtPrioridade.setText("Prioridade: " + prioridade);
        txtStatus.setText("Status: " + statusAtual);

        carregarDadosAtividade(id);

        btnSalvarAtividade.setOnClickListener(v -> {
            salvarDadosAtividade(id);

            Toast.makeText(
                    this,
                    "Dados da atividade salvos",
                    Toast.LENGTH_SHORT
            ).show();
        });

        btnFinalizarAtividade.setOnClickListener(v ->
                finalizarAtividade(id)
        );
    }
    private void salvarDadosAtividade(int id) {
        SharedPreferences preferences =
                getSharedPreferences("atividades", MODE_PRIVATE);

        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("diagnostico_" + id, edtDiagnostico.getText().toString());
        editor.putString("solucao_" + id, edtSolucao.getText().toString());
        editor.putString("materiais_" + id, edtMateriais.getText().toString());
        editor.putString("observacoes_" + id, edtObservacoes.getText().toString());

        editor.apply();

    }
    private void carregarDadosAtividade(int id) {
        SharedPreferences preferences =
                getSharedPreferences("atividades", MODE_PRIVATE);

        edtDiagnostico.setText(
                preferences.getString("diagnostico_" + id, "")
        );

        edtSolucao.setText(
                preferences.getString("solucao_" + id, "")
        );

        edtMateriais.setText(
                preferences.getString("materiais_" + id, "")
        );

        edtObservacoes.setText(
                preferences.getString("observacoes_" + id, "")
        );
    }
    private void finalizarAtividade(int id) {

        String diagnostico =
                edtDiagnostico.getText().toString().trim();

        String solucao =
                edtSolucao.getText().toString().trim();

        if (diagnostico.isEmpty()) {
            edtDiagnostico.setError("Informe o diagnóstico");
            edtDiagnostico.requestFocus();
            return;
        }

        if (solucao.isEmpty()) {
            edtSolucao.setError("Informe a solução aplicada");
            edtSolucao.requestFocus();
            return;
        }
        salvarDadosAtividade(id);

        SharedPreferences preferences =
                getSharedPreferences("atividades", MODE_PRIVATE);

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("status_" + id, "Finalizada");
        editor.apply();

        Toast.makeText(
                this,
                "Atividade finalizada com sucesso",
                Toast.LENGTH_SHORT
        ).show();

        finish();

    }
}