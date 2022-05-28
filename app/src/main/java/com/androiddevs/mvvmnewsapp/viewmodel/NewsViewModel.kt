package com.androiddevs.mvvmnewsapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Query
import com.androiddevs.mvvmnewsapp.Repository.NewsRepository
import com.androiddevs.mvvmnewsapp.Resources
import com.androiddevs.mvvmnewsapp.model.NewsResponse
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(val newsRepository: NewsRepository): ViewModel() {

    val breakingNews : MutableLiveData<Resources<NewsResponse>> = MutableLiveData()
    var breakingNewsPage = 1

    val searchNews : MutableLiveData<Resources<NewsResponse>> = MutableLiveData()
    var searchNewsPage = 1

    init {
        getBreakingNews("us")
    }

    fun getBreakingNews(countryCode : String) = viewModelScope.launch {
        breakingNews.postValue(Resources.Loading())
        val response = newsRepository.getBreakingNews(countryCode,breakingNewsPage)
        breakingNews.postValue(handleBreakingNewsResponse(response))

    }

    fun searchNews(searchQuery: String) = viewModelScope.launch {
         searchNews.postValue(Resources.Loading())
        val response = newsRepository.searchForNews(searchQuery,searchNewsPage)
        searchNews.postValue(handleBreakingNewsResponse(response))
    }

    private fun handleBreakingNewsResponse(  response: Response<NewsResponse>): Resources<NewsResponse>
    {
        if (response.isSuccessful)
        {   response.body()?.let { resultResponse ->
            return Resources.Success(resultResponse) }

        }

        return  Resources.Error(response.message())
    }
}

