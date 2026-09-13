package com.manbile.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.manbile.app.models.Atividade;

public class AtividadesActivity extends AppCompatActivity {

    private TextView txtAtividade1025;
    private TextView txtAtividade1026;
    private TextView txtAtividade1027;
    private Atividade atividade1025;
    private Atividade atividade1026;
    private Atividade atividade1027;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_atividades);

        // Liga os TextViews do Java ao XML
        txtAtividade1025 = findViewById(R.id.txtAtividade1025);
        txtAtividade1026 = findViewById(R.id.txtAtividade1026);
        txtAtividade1027 = findViewById(R.id.txtAtividade1027);

        // Criação das atividades
        atividade1025 = new Atividade(
                1025,
                "Empresa Alfa",
                "Corretiva",
                "Urgente",
                "Em deslocamento",
                "89620000"
        );

        atividade1026 = new Atividade(
                1026,
                "Empresa Beta",
                "Preventiva",
                "Normal",
                "Atribuída",
                "88010000"
        );

        atividade1027 = new Atividade(
                1027,
                "Empresa Gama",
                "Serviço",
                "Alta",
                "No local",
                "89201000"
        );
        // Clique nas atividades
        txtAtividade1025.setOnClickListener(v ->
                abrirDetalhes(atividade1025)
        );

        txtAtividade1026.setOnClickListener(v ->
                abrirDetalhes(atividade1026)
        );

        txtAtividade1027.setOnClickListener(v ->
                abrirDetalhes(atividade1027)
        );
    }

    private void carregarEndereco(
            Atividade atividade,
            TextView textView,
            String status
    ) {

        ViaCepApi api = RetrofitClient
                .getRetrofit()
                .create(ViaCepApi.class);

        api.buscarEndereco(atividade.getCep())
                .enqueue(new retrofit2.Callback<Endereco>() {

                    @Override
                    public void onResponse(
                            retrofit2.Call<Endereco> call,
                            retrofit2.Response<Endereco> response) {

                        if (response.isSuccessful()
                                && response.body() != null) {

                            Endereco endereco = response.body();

                            SharedPreferences preferences =
                                    getSharedPreferences(
                                            "atividades",
                                            MODE_PRIVATE
                                    );

                            String status1025 =
                                    preferences.getString(
                                            "status_1025",
                                            "Em deslocamento"
                                    );

                            textView.setText(
                                    "OS #" + atividade.getId() + "\n" +
                                            "Cliente: " + atividade.getCliente() + "\n" +
                                            "Tipo: " + atividade.getTipo() + "\n" +
                                            "Prioridade: " + atividade.getPrioridade() + "\n" +
                                            "Status: " + status + "\n" +
                                            "Local: " + endereco.getLocalidade() +
                                            " - " + endereco.getUf()
                            );

                        } else {

                            Toast.makeText(
                                    AtividadesActivity.this,
                                    "Erro ao consultar CEP",
                                    Toast.LENGTH_LONG
                            ).show();
                        }
                    }

                    @Override
                    public void onFailure(
                            retrofit2.Call<Endereco> call,
                            Throwable t) {

                        Toast.makeText(
                                AtividadesActivity.this,
                                "Falha na conexão: " +
                                        t.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                });
    }

    private void abrirDetalhes(Atividade atividade) {

        SharedPreferences preferences =
                getSharedPreferences("atividades", MODE_PRIVATE);

        String statusAtual = preferences.getString(
                "status_" + atividade.getId(),
                atividade.getStatus()
        );

        Intent intent = new Intent(
                AtividadesActivity.this,
                DetalhesAtividadeActivity.class
        );

        intent.putExtra("id", atividade.getId());
        intent.putExtra("cliente", atividade.getCliente());
        intent.putExtra("tipo", atividade.getTipo());
        intent.putExtra("prioridade", atividade.getPrioridade());
        intent.putExtra("status", statusAtual);

        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences preferences =
                getSharedPreferences(
                        "atividades",
                        MODE_PRIVATE
                );

        String status1025 =
                preferences.getString(
                        "status_1025",
                        "Em deslocamento"
                );

        String status1026 =
                preferences.getString(
                        "status_1026",
                        "Atribuída"
                );

        String status1027 =
                preferences.getString(
                        "status_1027",
                        "No local"
                );

        txtAtividade1025.setText(
                "OS #1025\n" +
                        "Cliente: Empresa Alfa\n" +
                        "Tipo: Corretiva\n" +
                        "Prioridade: Urgente\n" +
                        "Status: " + status1025
        );

        txtAtividade1026.setText(
                "OS #1026\n" +
                        "Cliente: Empresa Beta\n" +
                        "Tipo: Preventiva\n" +
                        "Prioridade: Normal\n" +
                        "Status: " + status1026
        );

        txtAtividade1027.setText(
                "OS #1027\n" +
                        "Cliente: Empresa Gama\n" +
                        "Tipo: Serviço\n" +
                        "Prioridade: Alta\n" +
                        "Status: " + status1027
        );

        // Consulta novamente o endereço sempre que
        // a tela de atividades volta a aparecer
        carregarEndereco(
                atividade1025,
                txtAtividade1025,
                status1025
        );

        carregarEndereco(
                atividade1026,
                txtAtividade1026,
                status1026
        );

        carregarEndereco(
                atividade1027,
                txtAtividade1027,
                status1027
        );
    }
}