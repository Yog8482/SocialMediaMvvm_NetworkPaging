package com.yogendra.socialmediamvvm.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PagedList
import com.yogendra.socialmediamvvm.data.Articles
import com.yogendra.socialmediamvvm.data.Users
import com.yogendra.socialmediamvvm.datasource.local.ArticlesDao
import com.yogendra.socialmediamvvm.datasource.local.UsersDao
import com.yogendra.socialmediamvvm.datasource.remote.ArticlesPageDataSource
import com.yogendra.socialmediamvvm.datasource.remote.ArticlesRemoteDataSource
import com.yogendra.socialmediamvvm.datasource.remote.UsersPageDataSource
import com.yogendra.socialmediamvvm.datasource.remote.UsersRemoteDataSource
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class ArticlesPageDataSourceFactory @Inject constructor(
    private val dataSource: ArticlesRemoteDataSource,
    private val dao: ArticlesDao,
    private val scope: CoroutineScope
) : DataSource.Factory<Int, Articles>() {

    private val articlesliveData = MutableLiveData<ArticlesPageDataSource>()

    fun getMutableLiveData(): MutableLiveData<ArticlesPageDataSource> {
        return articlesliveData
    }

    override fun create(): DataSource<Int, Articles> {
        val source = ArticlesPageDataSource(dataSource, dao, scope)
        articlesliveData.postValue(source)
        return source
    }

    companion object {
        private const val PAGE_SIZE = 10

        fun pagedListConfig() = PagedList.Config.Builder()
            .setInitialLoadSizeHint(PAGE_SIZE)
            .setPageSize(PAGE_SIZE)
            .setEnablePlaceholders(true)
            .build()
    }

}

class UsersPageDataSourceFactory @Inject constructor(
    private val usersRemoteDataSource: UsersRemoteDataSource,
    private val dao: UsersDao,
    private val scope: CoroutineScope
) : DataSource.Factory<Int, Users>() {

    private val usersliveData = MutableLiveData<UsersPageDataSource>()

    override fun create(): DataSource<Int, Users> {
        val source = UsersPageDataSource(usersRemoteDataSource, dao, scope)
        usersliveData.postValue(source)
        return source
    }

    companion object {
        private const val PAGE_SIZE = 10

        fun pagedListConfig() = PagedList.Config.Builder()
            .setInitialLoadSizeHint(PAGE_SIZE)
            .setPageSize(PAGE_SIZE)
            .setEnablePlaceholders(true)
            .build()
    }
}