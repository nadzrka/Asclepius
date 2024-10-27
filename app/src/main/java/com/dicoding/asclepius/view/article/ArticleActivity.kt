@file:Suppress("unused", "RedundantSuppression")

package com.dicoding.asclepius.view.article

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.asclepius.databinding.ActivityArticleBinding
import com.dicoding.asclepius.view.ViewModelFactory

class ArticleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityArticleBinding
    private val articleViewModel: ArticleViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var articleAdapter: ArticleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        articleAdapter = ArticleAdapter()
        binding.rvArticle.layoutManager = LinearLayoutManager(this)
        binding.rvArticle.adapter = articleAdapter

        articleViewModel.articles.observe(this) { articles ->
            val filteredArticles = articles.filter { it.author != null }
            articleAdapter.submitList(filteredArticles)
        }
        articleViewModel.getArticle()
    }

}
