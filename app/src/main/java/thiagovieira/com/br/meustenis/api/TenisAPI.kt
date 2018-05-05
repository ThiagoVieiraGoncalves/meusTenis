package thiagovieira.com.br.meustenis.api

import retrofit2.Call
import retrofit2.http.*
import thiagovieira.com.br.meustenis.model.Tenis

interface TenisAPI {

    @GET("/tenis")
    fun buscarTodos() : Call<List<Tenis>>

    @POST("/tenis")
    fun salvar(@Body tenis: Tenis): Call<Void>

    @GET("/tenis/{id}")
    fun buscaPeloId(@Path("id") id: String): Call<Tenis>

    @DELETE("/tenis/{id}")
    fun remover(@Path("id") id: String): Call<Void>
}