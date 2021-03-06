package com.yogendra.socialmediamvvm.datasource.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yogendra.socialmediamvvm.data.Articles
import com.yogendra.socialmediamvvm.data.Users

@Dao
interface ArticlesDao {

    @Query("SELECT * FROM article")
    fun getPagedArticles(): DataSource.Factory<Int, Articles>

    @Query("SELECT * FROM article")
    fun getArticles(): LiveData<List<Articles>>

    @Query("SELECT * FROM article WHERE id= :article_id ")
    fun getArticleUser(article_id: String): LiveData<Articles>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(articles: List<Articles>)
}