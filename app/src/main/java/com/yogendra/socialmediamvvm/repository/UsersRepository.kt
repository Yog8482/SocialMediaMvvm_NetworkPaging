package com.yogendra.socialmediamvvm.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.distinctUntilChanged
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.yogendra.socialmediamvvm.data.Articles
import com.yogendra.socialmediamvvm.data.Users
import com.yogendra.socialmediamvvm.data.resultLiveData
import com.yogendra.socialmediamvvm.datasource.ArticlesPageDataSourceFactory
import com.yogendra.socialmediamvvm.datasource.UsersPageDataSourceFactory
import com.yogendra.socialmediamvvm.datasource.local.dao.ArticlesDao
import com.yogendra.socialmediamvvm.datasource.local.dao.UsersDao
import com.yogendra.socialmediamvvm.datasource.remote.ArticlesRemoteDataSource
import com.yogendra.socialmediamvvm.datasource.remote.UsersRemoteDataSource
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class UsersRepository @Inject constructor(
    private val dao: UsersDao,
    private val remoteDataSource: UsersRemoteDataSource
) {

    fun observePagedSets(connectivityAvailable: Boolean, coroutineScope: CoroutineScope) =
        if (connectivityAvailable) observeRemotePagedSets(coroutineScope)
        else observeLocalPagedSets()


    private fun observeLocalPagedSets(): LiveData<PagedList<Users>> {
        val dataSourceFactory =
            dao.getPagedUsers()

        return LivePagedListBuilder(
            dataSourceFactory,
            ArticlesPageDataSourceFactory.pagedListConfig()
        ).build()
    }

    fun observeUsers() = resultLiveData(
        databaseQuery = { dao.getUsers() },
        networkCall = { remoteDataSource.fetchUsers() },
        saveCallResult = { dao.insertAll(it) })
        .distinctUntilChanged()

    private fun observeRemotePagedSets(ioCoroutineScope: CoroutineScope)
            : LiveData<PagedList<Users>> {
        val dataSourceFactory = UsersPageDataSourceFactory(
            remoteDataSource,
            dao, ioCoroutineScope
        )
        return LivePagedListBuilder(
            dataSourceFactory,
            ArticlesPageDataSourceFactory.pagedListConfig() //Same page config for both
        ).build()
    }
}