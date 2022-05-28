package com.example.newsapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment

import android.view.View

import com.androiddevs.mvvmnewsapp.R
import com.androiddevs.mvvmnewsapp.view.NewActivity
import com.androiddevs.mvvmnewsapp.viewmodel.NewsViewModel


class SavedNews : Fragment(R.layout.fragment_saved_news) {

    lateinit var newsViewModel: NewsViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newsViewModel = (activity as NewActivity).newsViewModel
    }
}