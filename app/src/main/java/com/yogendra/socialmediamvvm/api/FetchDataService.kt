package com.yogendra.socialmediamvvm.api


import com.yogendra.socialmediamvvm.data.Articles
import com.yogendra.socialmediamvvm.data.Users
import com.yogendra.socialmediamvvm.utils.BASE_URL
import com.yogendra.socialmediamvvm.utils.DEFAULT_URL_PARAMETRES_ARTICLE
import com.yogendra.socialmediamvvm.utils.URL_PARAMETRES_USERS
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * REST API access points
 */
interface FetchDataService {

    @GET(DEFAULT_URL_PARAMETRES_ARTICLE)
    suspend fun getArticles(
        @Query("page") page: Int? = null,
        @Query("limit") pageSize: Int? = null
    ): Response<List<Articles>>

    @GET(URL_PARAMETRES_USERS)
    suspend fun getUsers(
        @Query("page") page: Int? = null,
        @Query("limit") pageSize: Int? = null
    ): Response<List<Users>>


}


