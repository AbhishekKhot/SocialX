package com.example.quantumit.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.quantumit.R
import com.example.quantumit.model.Article
import kotlinx.android.synthetic.main.rv_item.view.*

class NewsAdapter(var list:MutableList<Article>):RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    inner class NewsViewHolder(itemView:View):RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.rv_item,parent,false))
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = list[position]
        holder.itemView.apply {
            tvPublishedAt.text=article.publishedAt
            tvSource.text=article.source.name
            tvTitle.text=article.title
            tvDescription.text=article.description
            Glide.with(this).load(article.urlToImage).placeholder(R.drawable.ic_baseline_image_24).into(ivArticleImage)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun filterAdapter(filteredArticles:MutableList<Article>){
        list.clear()
        for (article in filteredArticles){
            list.add(article)
        }
        notifyDataSetChanged()
    }
}