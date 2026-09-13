package com.manbile.app;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ViaCepApi {

    @GET("ws/{cep}/json/")
    Call<Endereco> buscarEndereco(@Path("cep") String cep);
}