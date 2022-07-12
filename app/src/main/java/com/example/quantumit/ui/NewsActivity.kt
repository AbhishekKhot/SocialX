package com.example.quantumit.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quantumit.R
import com.example.quantumit.adapter.NewsAdapter
import com.example.quantumit.repository.NewsRepository
import com.example.quantumit.util.Resource
import kotlinx.android.synthetic.main.activity_news.*
import kotlinx.android.synthetic.main.activity_news.progressBar
import kotlinx.android.synthetic.main.activity_news.recyclerView

class NewsActivity : AppCompatActivity() {
    lateinit var viewModel: NewsViewModel
    val newsAdapter = NewsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        setSupportActionBar(toolbar)

        val repository = NewsRepository()
        val viewModelProviderFactory = NewsViewModelProviderFactory(repository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(NewsViewModel::class.java)

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
                        newsAdapter.differ.submitList(it.articles)
                    }
                }
                is Resource.Error -> {
                    progressBar.visibility = View.INVISIBLE
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}