package com.yogendra.socialmediamvvm.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PagedList
import com.yogendra.socialmediamvvm.data.Articles
import com.yogendra.socialmediamvvm.datasource.local.dao.ArticlesDao
import com.yogendra.socialmediamvvm.datasource.remote.ArticlesPageDataSource
import com.yogendra.socialmediamvvm.datasource.remote.ArticlesRemoteDataSource
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class ArticlesPageDataSourceFactory @Inject constructor(
        private val dataSource: ArticlesRemoteDataSource,
        private val dao: ArticlesDao,
        private val scope: CoroutineScope) : DataSource.Factory<Int, Articles>() {

    private val liveData = MutableLiveData<ArticlesPageDataSource>()

    override fun create(): DataSource<Int, Articles> {
        val source = ArticlesPageDataSource(dataSource, dao, scope)
        liveData.postValue(source)
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