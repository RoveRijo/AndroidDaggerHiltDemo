package com.example.androiddaggerhiltdemo.views.home_screen

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater

import android.view.ViewGroup

import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.viewModels

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androiddaggerhiltdemo.R
import com.example.androiddaggerhiltdemo.databinding.ActivityNewsBinding
import com.example.androiddaggerhiltdemo.databinding.NewsContentDesignBinding
import com.example.androiddaggerhiltdemo.network.models.Article
import com.example.androiddaggerhiltdemo.views.web_view_screen.WebViewActivity
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

const val NEWS_URL = "news_url"

@AndroidEntryPoint
class NewsViewActivity : ComponentActivity() {
    private lateinit var binding: ActivityNewsBinding
    private val newsViewModel by viewModels<NewsViewModel>()
    @Inject lateinit var rv_adaptor:RVadaptor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.RVNews.layoutManager = LinearLayoutManager(this)
        binding.RVNews.adapter = rv_adaptor
        newsViewModel.loadData() // This function will trigger the dataload request to repository
        newsViewModel.observableErrorMessage.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()     // observing error
        })
        newsViewModel.observableNewsResponse.observe(this, Observer {
            rv_adaptor.submitList(it)     // observing and submitting list of news
        })
    }

    /**
     * RecyclerView adapter for displaying news
     */
    class RVadaptor @Inject constructor(private val context:Application) : RecyclerView.Adapter<RVadaptor.ViewHolder>() {
        private var articles: List<Article>? = null
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val binding =
                NewsContentDesignBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ViewHolder(
                binding
            )
        }

        fun submitList(articles: List<Article>) {
            this.articles = articles
            notifyDataSetChanged()
        }

        override fun getItemCount(): Int {
            return articles?.size ?: 0
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.apply {
                articles?.get(position)?.let { article ->
                    Picasso.get().load(article.urlToImage).placeholder(R.drawable.error)
                        .error(R.drawable.error).into(poster)
                    title.text = article.title

                    description.text = article.description
                    itemview.setOnClickListener {
                        val intent = Intent(
                            context, WebViewActivity::class.java
                        )
                        intent.putExtra(NEWS_URL, article.url)
                        context.startActivity(intent)
                    }
                }
            }

        }


        inner class ViewHolder(binding: NewsContentDesignBinding) :
            RecyclerView.ViewHolder(binding.root) {
            val title = binding.TVTitle
            val description = binding.TVDescription
            val poster = binding.IVPoster
            val itemview = binding.root
        }
    }
}
