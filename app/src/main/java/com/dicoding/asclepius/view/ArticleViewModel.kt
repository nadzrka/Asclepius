package com.dicoding.asclepius.view

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.asclepius.BuildConfig
import com.dicoding.asclepius.data.remote.response.ArticleResponse
import com.dicoding.asclepius.data.remote.response.ArticlesItem
import com.dicoding.asclepius.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ArticleViewModel : ViewModel() {

    private val _listArticle = MutableLiveData<List<ArticlesItem>>()
    val articles: LiveData<List<ArticlesItem>> get() = _listArticle // Ensure this is named 'articles'

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val TAG = "ArticleViewModel"

    fun getArticle() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getArticle(BuildConfig.API_KEY)
        client.enqueue(object : Callback<ArticleResponse> {
            override fun onResponse(
                call: Call<ArticleResponse>,
                response: Response<ArticleResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _listArticle.value = responseBody.articles?.filterNotNull() ?: emptyList()
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ArticleResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
}
