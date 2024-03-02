package com.example.androiddaggerhiltdemo.views.web_view_screen


import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import com.example.androiddaggerhiltdemo.databinding.ActivityWebViewBinding
import com.example.androiddaggerhiltdemo.views.home_screen.NEWS_URL


class WebViewActivity : ComponentActivity() {
    private lateinit var binding: ActivityWebViewBinding
    private var newsUrl: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        newsUrl = intent.getStringExtra(NEWS_URL)
        newsUrl?.let {
            binding.WBNews.webViewClient = NEWS()
            binding.WBNews.settings.javaScriptEnabled = true
            binding.WBNews.loadUrl(it)
        }
    }

    class NEWS : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            url?.let { view?.loadUrl(it) }
            return true
        }
    }
}
