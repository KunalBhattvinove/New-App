package com.example.newsapp.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.androiddevs.mvvmnewsapp.R
import com.androiddevs.mvvmnewsapp.Resources
import com.androiddevs.mvvmnewsapp.adapter.NewsAdapter
import com.androiddevs.mvvmnewsapp.view.NewActivity
import com.androiddevs.mvvmnewsapp.viewmodel.NewsViewModel
import kotlinx.android.synthetic.main.fragment_breaking_news.*


class BreakingNews : Fragment(R.layout.fragment_breaking_news) {

    lateinit var newsViewModel: NewsViewModel
    lateinit var newsAdapter: NewsAdapter
    private val TAG ="BreakingNewsFragment"


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newsViewModel = (activity as NewActivity).newsViewModel
        setupRecyclerView()

        newsViewModel.breakingNews.observe(viewLifecycleOwner, Observer { response ->
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
            rvBreakingNews.apply {
                adapter = newsAdapter
                layoutManager= LinearLayoutManager(activity)
            }
        }
}