package com.example.quantumit.repository

import com.example.quantumit.network.RetrofitInstance

class NewsRepository {
    suspend fun getNewsArticles(countryCode: String, pageNumber: Int) =
        RetrofitInstance.api.getNewsArticles(countryCode, pageNumber)
}