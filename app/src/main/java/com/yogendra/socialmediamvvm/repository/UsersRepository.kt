package com.yogendra.socialmediamvvm.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.distinctUntilChanged
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.yogendra.socialmediamvvm.data.Users
import com.yogendra.socialmediamvvm.data.resultLiveData
import com.yogendra.socialmediamvvm.data.resultLocalLiveData
import com.yogendra.socialmediamvvm.datasource.UsersPageDataSourceFactory
import com.yogendra.socialmediamvvm.datasource.local.UsersDao
import com.yogendra.socialmediamvvm.datasource.remote.UsersRemoteDataSource
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
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
            UsersPageDataSourceFactory.pagedListConfig()
        ).build()
    }

    fun observeUsers() = resultLiveData(
        databaseQuery = { dao.getUsers() },
        networkCall = { remoteDataSource.fetchUsers() },
        saveCallResult = { dao.insertAll(it) })
        .distinctUntilChanged()

    fun observeArticleProfile(articleId: String) = resultLocalLiveData(
        databaseQuery = { dao.getArticleUserProfile(articleId) })
        .distinctUntilChanged()

    fun observeUserProfile(userId: String) = resultLocalLiveData(
        databaseQuery = { dao.getUserProfile(userId) })
        .distinctUntilChanged()

    private fun observeRemotePagedSets(ioCoroutineScope: CoroutineScope)
            : LiveData<PagedList<Users>> {
        val dataSourceFactory = UsersPageDataSourceFactory(
            remoteDataSource,
            dao, ioCoroutineScope
        )
        return LivePagedListBuilder(
            dataSourceFactory,
            UsersPageDataSourceFactory.pagedListConfig() //Same page config for both
        ).build()
    }



}