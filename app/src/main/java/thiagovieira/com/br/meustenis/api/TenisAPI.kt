package thiagovieira.com.br.meustenis.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import thiagovieira.com.br.meustenis.model.Tenis

interface TenisAPI {

    @GET("/tenis")
    fun buscarTodos() : Call<List<Tenis>>

    @POST("/tenis")
    fun salvar(@Body tenis: Tenis): Call<Void>
}