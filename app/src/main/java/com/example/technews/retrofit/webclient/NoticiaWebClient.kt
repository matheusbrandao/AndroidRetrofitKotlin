package com.example.technews.retrofit.webclient

import com.example.technews.model.Noticia
import retrofit2.Call
import com.example.technews.retrofit.AppRetrofit
import com.example.technews.retrofit.service.NoticiaService
import retrofit2.Response
import retrofit2.Callback

private const val REQUISICAO_NAO_SUCEDIDA = "Requisição não sucedida"

class NoticiaWebClient (
    private val service: NoticiaService = AppRetrofit().noticiaService
) {

    private fun <T> executaRequisicao(
        call: Call<T>,
        quandoSucesso: (noticiasNovas: T?) -> Unit,
        quandoFalha: (erro: String?) -> Unit
    ) {
        call.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (response.isSuccessful)
                    quandoSucesso(response.body())
                else
                    quandoFalha(REQUISICAO_NAO_SUCEDIDA)
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                quandoFalha(t.message)
            }
        })
    }

    fun buscaTodas(
        quandoSucesso: (noticiasNovas: List<Noticia>?) -> Unit,
        quandoFalha: (erro: String?) -> Unit
    ) {
        executaRequisicao(
            service.buscaTodas(),
            quandoSucesso,
            quandoFalha
        )
    }
}