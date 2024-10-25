@file:Suppress("unused", "RedundantSuppression")

package com.dicoding.asclepius.data.remote.retrofit

import com.dicoding.asclepius.data.remote.response.ArticleResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("top-headlines?country=us&category=health&q=cancer")
    fun getArticle(@Query("apiKey") apiKey: String): Call<ArticleResponse>
}
