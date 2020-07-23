package com.yogendra.socialmediamvvm.datasource.remote

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.yogendra.socialmediamvvm.data.Articles
import com.yogendra.socialmediamvvm.data.Result
import com.yogendra.socialmediamvvm.datasource.local.ArticlesDao
import com.yogendra.socialmediamvvm.utils.ProgressStatus
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class ArticlesPageDataSource @Inject constructor(
    private val dataSource: ArticlesRemoteDataSource,
    private val dao: ArticlesDao,
    private val scope: CoroutineScope
) : PageKeyedDataSource<Int, Articles>() {
    private val progressLiveStatus: MutableLiveData<String> = MutableLiveData()


    fun getProgressLiveStatus(): MutableLiveData<String> {
        return progressLiveStatus
    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Articles>
    ) {
        fetchData(1, params.requestedLoadSize) {
            callback.onResult(it, null, 2)
        }

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Articles>) {
        val page = params.key
        fetchData(page, params.requestedLoadSize) {
            callback.onResult(it, page + 1)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Articles>) {
        val page = params.key
        fetchData(page, params.requestedLoadSize) {
            callback.onResult(it, page - 1)
        }
    }


    private fun fetchData(page: Int, pageSize: Int, callback: (List<Articles>) -> Unit) {
        scope.launch(getJobErrorHandler()) {
            progressLiveStatus.postValue(ProgressStatus.LOADING.toString())
            val response = dataSource.fetchArticles(page, pageSize)
            if (response.status == Result.Status.SUCCESS) {
                val results = response.data!!
                dao.insertAll(results)
                callback(results)
                progressLiveStatus.postValue(ProgressStatus.COMPLTED.toString())

            } else if (response.status == Result.Status.ERROR) {
                postError(response.message!!)
            }
        }
    }

    private fun getJobErrorHandler() = CoroutineExceptionHandler { _, e ->
        postError(e.message ?: e.toString())
    }

    private fun postError(message: String) {
        Log.e("ArticlesPageDataSource", "An error happened: $message")
        // TODO network error handling
        if (message.contains("Unable to resolve host")) {
            progressLiveStatus.postValue(ProgressStatus.NO_NETWORK.toString())
        } else
            progressLiveStatus.postValue(ProgressStatus.ERROR.toString())

    }
}