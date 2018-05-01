package thiagovieira.com.br.meustenis.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import thiagovieira.com.br.meustenis.model.Usuario

interface UsuarioAPI {

    @POST("/usuario")
    fun salvar(@Body usuario: Usuario) : Call<Void>

    @POST("/usuario/login")
    fun login(@Body usuario: Usuario): Call<Usuario>
}