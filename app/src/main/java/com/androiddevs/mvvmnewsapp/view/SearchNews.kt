package com.example.newsapp.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment

import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.androiddevs.mvvmnewsapp.Constant.Companion.SEARCH_NEWS_TIME_DELAY

import com.androiddevs.mvvmnewsapp.R
import com.androiddevs.mvvmnewsapp.Resources
import com.androiddevs.mvvmnewsapp.adapter.NewsAdapter
import com.androiddevs.mvvmnewsapp.view.NewActivity
import com.androiddevs.mvvmnewsapp.viewmodel.NewsViewModel
import kotlinx.android.synthetic.main.fragment_breaking_news.*
import kotlinx.android.synthetic.main.fragment_breaking_news.paginationProgressBar
import kotlinx.android.synthetic.main.fragment_search_news.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SearchNews : Fragment(R.layout.fragment_search_news) {

    lateinit var newsViewModel: NewsViewModel
    private val TAG ="SearchNewsFragment"
    lateinit var newsAdapter:NewsAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newsViewModel = (activity as NewActivity).newsViewModel
        setupRecyclerView()

        var job:Job?= null
        etSearch.addTextChangedListener { editable ->
            job?.cancel()
            job = MainScope().launch {
                delay(SEARCH_NEWS_TIME_DELAY)
                editable?. let{
                    if (editable.toString().isEmpty())
                    {
                        newsViewModel.searchNews(editable.toString())

                    }
                }
            }
        }

        newsViewModel.searchNews.observe(viewLifecycleOwner, Observer { response ->
            when(response)
            {
                is Resources.Success ->{
                    hideProgressBar()
                    response.data?.let { newsResponse ->
                        newsAdapter.differ.submitList(newsResponse.articles)
                    }
                }
                is Resources.Error ->{
                    hideProgressBar()
                    response.message.let { message ->
                        Log.d(TAG, "An error occured: $message")

                    }
                }
                is Resources.Loading ->{
                    showProgressBar()
                }
            }
        })
    }

    private fun showProgressBar() {
        paginationProgressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        paginationProgressBar.visibility = View.INVISIBLE
    }

    private fun setupRecyclerView()
    {
        newsAdapter = NewsAdapter()
        rvSearchNews.apply {
            adapter = newsAdapter
            layoutManager= LinearLayoutManager(activity)
        }
    }
}