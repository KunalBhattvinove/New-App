package com.example.newsapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.androiddevs.mvvmnewsapp.R
import com.androiddevs.mvvmnewsapp.view.NewActivity
import com.androiddevs.mvvmnewsapp.viewmodel.NewsViewModel


class ArticlePreview : Fragment(R.layout.item_article_preview) {

    lateinit var newsViewModel: NewsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newsViewModel = (activity as NewActivity).newsViewModel
    }
    }

