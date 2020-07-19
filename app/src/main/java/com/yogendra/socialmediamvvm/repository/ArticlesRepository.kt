package com.yogendra.socialmediamvvm.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.distinctUntilChanged
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.yogendra.socialmediamvvm.data.Articles
import com.yogendra.socialmediamvvm.data.resultLiveData
import com.yogendra.socialmediamvvm.datasource.ArticlesPageDataSourceFactory
import com.yogendra.socialmediamvvm.datasource.local.dao.ArticlesDao
import com.yogendra.socialmediamvvm.datasource.remote.ArticlesRemoteDataSource
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository module for handling data operations.
 */
@Singleton
class ArticlesRepository @Inject constructor(
    private val dao: ArticlesDao,
    private val remoteDataSource: ArticlesRemoteDataSource
) {
    fun observePagedSets(connectivityAvailable: Boolean, coroutineScope: CoroutineScope) =
        if (connectivityAvailable) observeRemotePagedSets(coroutineScope)
        else observeLocalPagedSets()


    private fun observeLocalPagedSets(): LiveData<PagedList<Articles>> {
        val dataSourceFactory =
            dao.getPagedArticles()

        return LivePagedListBuilder(
            dataSourceFactory,
            ArticlesPageDataSourceFactory.pagedListConfig()
        ).build()
    }

    fun observeArticles() = resultLiveData(
        databaseQuery = { dao.getArticles() },
        networkCall = { remoteDataSource.fetchArticles() },
        saveCallResult = { dao.insertAll(it) })
        .distinctUntilChanged()

    private fun observeRemotePagedSets(ioCoroutineScope: CoroutineScope)
            : LiveData<PagedList<Articles>> {
        val dataSourceFactory = ArticlesPageDataSourceFactory(
            remoteDataSource,
            dao, ioCoroutineScope
        )
        return LivePagedListBuilder(
            dataSourceFactory,
            ArticlesPageDataSourceFactory.pagedListConfig()
        ).build()
    }

}