package com.yogendra.socialmediamvvm.datasource.remote

import com.yogendra.socialmediamvvm.api.BaseDataSource
import com.yogendra.socialmediamvvm.api.FetchDataService
import javax.inject.Inject

class ArticlesRemoteDataSource @Inject constructor(private val service: FetchDataService) :
    BaseDataSource() {
    suspend fun fetchArticles(page: Int=1, pageSize: Int=10) =
        getResult { service.getArticles(page, pageSize) }
}

class UsersRemoteDataSource @Inject constructor(private val service: FetchDataService) :
    BaseDataSource() {
    suspend fun fetchUsers(page: Int=1, pageSize: Int = 10) =
        getResult {
            service.getUsers(page, pageSize)
        }
}