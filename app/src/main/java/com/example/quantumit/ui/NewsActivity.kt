package com.example.quantumit.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quantumit.R
import com.example.quantumit.adapter.NewsAdapter
import com.example.quantumit.model.Article
import com.example.quantumit.repository.NewsRepository
import com.example.quantumit.util.Resource
import kotlinx.android.synthetic.main.activity_news.*


class NewsActivity : AppCompatActivity() {
    lateinit var viewModel: NewsViewModel
    lateinit var newsAdapter: NewsAdapter
    var list= mutableListOf<Article>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        setSupportActionBar(toolbar)

        val repository = NewsRepository()
        val viewModelProviderFactory = NewsViewModelProviderFactory(repository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(NewsViewModel::class.java)

        newsAdapter= NewsAdapter(list)

        recyclerView.apply {
            this.layoutManager = LinearLayoutManager(this@NewsActivity)
            this.adapter = newsAdapter
        }

        viewModel.newsArticles.observe(this, Observer {
            when (it) {
                is Resource.Loading -> {
                    progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    progressBar.visibility = View.INVISIBLE
                    it.data?.let {
                        it.articles.forEach { article->
                            list.add(article)
                        }
                        newsAdapter.notifyDataSetChanged()
                    }
                }
                is Resource.Error -> {
                    progressBar.visibility = View.INVISIBLE
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })


       searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
           androidx.appcompat.widget.SearchView.OnQueryTextListener {
           override fun onQueryTextSubmit(p0: String?): Boolean {
               return false
           }

           override fun onQueryTextChange(query: String?): Boolean {
               filterRecyclerItems(query!!)
               return true
           }
       })
    }

    private fun filterRecyclerItems(query: String) {
        val filteredList = mutableListOf<Article>()
        for(article in list){
            if(article.title.toLowerCase().contains(query.toLowerCase())){
                filteredList.add(article)
            }
        }
        newsAdapter.filterAdapter(filteredList)
    }
}