package com.androiddevs.mvvmnewsapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.androiddevs.mvvmnewsapp.model.Article

@Dao
abstract class ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun upsert(article: Article): Long

    @Query("Select * from article")
    abstract fun  getAllArticle():LiveData<List<Article>>

    @Delete
    abstract suspend fun deleteArticle(article: Article)

}