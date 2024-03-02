package com.example.androiddaggerhiltdemo.views.home_screen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.androiddaggerhiltdemo.network.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(val repository: NewsRepository) : ViewModel() {
    val observableNewsResponse = repository.observableArticles
    val observableErrorMessage = repository.errorMessage
    fun loadData() {
        repository.requestNews()
    }

}