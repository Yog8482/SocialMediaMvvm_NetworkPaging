package com.yogendra.socialmediamvvm.datasource.remote

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.yogendra.socialmediamvvm.data.Result
import com.yogendra.socialmediamvvm.data.Users
import com.yogendra.socialmediamvvm.datasource.local.UsersDao
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class UsersPageDataSource @Inject constructor(
    private val dataSource: UsersRemoteDataSource,
    private val dao: UsersDao,
    private val scope: CoroutineScope
) : PageKeyedDataSource<Int, Users>() {

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Users>
    ) {
        fetchData(1, params.requestedLoadSize) {
            callback.onResult(it, null, 2)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Users>) {
        val page = params.key
        fetchData(page, params.requestedLoadSize) {
            callback.onResult(it, page + 1)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Users>) {
        val page = params.key
        fetchData(page, params.requestedLoadSize) {
            callback.onResult(it, page - 1)
        }
    }

    private fun fetchData(page: Int, pageSize: Int, callback: (List<Users>) -> Unit) {
        scope.launch(getJobErrorHandler()) {
            val response = dataSource.fetchUsers(page, pageSize)
            if (response.status == Result.Status.SUCCESS) {
                val results = response.data!!
                dao.insertAll(results)
                callback(results)
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
    }
}