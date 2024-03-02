package com.example.androiddaggerhiltdemo.network

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.androiddaggerhiltdemo.network.models.Article
import com.example.androiddaggerhiltdemo.network.models.Data
import com.example.udhartask.network.NewsAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class NewsRepository @Inject constructor(val context: Application, val newsAPI:NewsAPI) {
    val errorMessage = MutableLiveData<String>()
    val observableArticles = MutableLiveData<List<Article>>()
    fun requestNews() {
        val call = newsAPI.getNews(SOURCE, API_KEY)
        call.enqueue(object : Callback<Data> {
            override fun onFailure(call: Call<Data>, t: Throwable) {
                errorMessage.postValue("Couldn't reach server!")
                t.printStackTrace()
            }

            override fun onResponse(call: Call<Data>, response: Response<Data>) {
                if (response.isSuccessful) {
                    val newsList = response.body()?.articles
                    observableArticles.postValue(newsList)
                } else {
                    errorMessage.postValue("Server response error!")
                }
            }

        })
    }
}