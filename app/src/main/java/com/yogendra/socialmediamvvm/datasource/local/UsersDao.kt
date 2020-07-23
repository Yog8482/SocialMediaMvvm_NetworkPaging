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
interface UsersDao {


    @Query("SELECT * FROM users WHERE blogId is null")
    fun getPagedUsers(): DataSource.Factory<Int, Users>

    @Query("SELECT * FROM users WHERE blogId is null")
    fun getUsers(): LiveData<List<Users>>

    @Query("SELECT * FROM article WHERE id=:articleId")
    fun getArticleUserProfile(articleId: String): LiveData<Articles>

    @Query("SELECT * FROM users WHERE id=:userId")
    fun getUserProfile(userId: String): LiveData<Users>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<Users>)
}