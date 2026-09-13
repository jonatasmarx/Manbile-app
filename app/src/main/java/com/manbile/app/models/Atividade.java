package com.manbile.app.models;

public class Atividade {

    private int id;
    private String cliente;
    private String tipo;
    private String prioridade;
    private String status;
    private String cep;
    private String diagnostico;
    private String solucao;
    private String materiais;
    private String observacoes;

    public Atividade(
            int id,
            String cliente,
            String tipo,
            String prioridade,
            String status,
            String cep
    ) {
        this.id = id;
        this.cliente = cliente;
        this.tipo = tipo;
        this.prioridade = prioridade;
        this.status = status;
        this.cep = cep;
    }

    public int getId() {
        return id;
    }

    public String getCliente() {
        return cliente;
    }

    public String getTipo() {
        return tipo;
    }

    public String getPrioridade() {
        return prioridade;
    }

    public String getStatus() {
        return status;
    }

    public String getCep() {
        return cep;
    }
    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getSolucao() {
        return solucao;
    }

    public void setSolucao(String solucao) {
        this.solucao = solucao;
    }

    public String getMateriais() {
        return materiais;
    }

    public void setMateriais(String materiais) {
        this.materiais = materiais;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }
}