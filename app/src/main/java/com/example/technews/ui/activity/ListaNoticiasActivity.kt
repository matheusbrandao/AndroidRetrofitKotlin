package com.example.technews.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import com.example.technews.R
import com.example.technews.database.AppDatabase
import com.example.technews.model.Noticia
import com.example.technews.repository.NoticiaRepository
import com.example.technews.ui.activity.extensions.mostraErro
import com.example.technews.ui.recyclerview.adapter.ListaNoticiasAdapter
import kotlinx.android.synthetic.main.activity_lista_noticias.*

private const val TITULO_APPBAR = "Notícias"
private const val MENSAGEM_FALHA_CARREGAR_NOTICIAS = "Não foi possível carregar as novas notícias"

class ListaNoticiasActivity : AppCompatActivity() {

    private val adapter by lazy {
        ListaNoticiasAdapter(context = this)
    }

    private val repository by lazy {
        NoticiaRepository(AppDatabase.getInstance(this).noticiaDAO)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_noticias)
        title = TITULO_APPBAR
        configurarRecyclerView()
        configuraFabAdicionaNoticia()
    }

    override fun onResume() {
        super.onResume()
        buscaNoticias()
    }

    private fun configuraFabAdicionaNoticia() {
        activity_lista_noticias_fab_salva_noticia.setOnClickListener {
            abreFormularioModoCriacao()
        }
    }

    private fun configurarRecyclerView() {
        val divisor = DividerItemDecoration(this, VERTICAL)
        activity_lista_noticias_recyclerview.addItemDecoration(divisor)
        activity_lista_noticias_recyclerview.adapter = adapter
        configurarAdapter()
    }

    private fun configurarAdapter() {
        adapter.quandoItemClicado = this::abreVisualizadorNoticia
    }

    private fun buscaNoticias() {
        repository.buscaTodos(
            quandoSucesso = {
                adapter.atualiza(it)
            }, quandoFalha = {
                mostraErro(MENSAGEM_FALHA_CARREGAR_NOTICIAS)
            }
        )
    }

    private fun abreFormularioModoCriacao() {
        val intent = Intent(this, FormularioNoticiaActivity::class.java)
        startActivity(intent)
    }

    private fun abreVisualizadorNoticia(it: Noticia) {
        Toast.makeText(this, "Clicou", Toast.LENGTH_LONG).show()
    }
}
