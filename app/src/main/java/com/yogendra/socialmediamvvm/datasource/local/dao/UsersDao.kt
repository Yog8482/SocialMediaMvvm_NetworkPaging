package com.yogendra.socialmediamvvm.datasource.local.dao

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yogendra.socialmediamvvm.data.Users

interface UsersDao {


    @Query("SELECT * FROM users WHERE blogId=null ORDER BY createdAt DESC")
    fun getPagedUsers(): DataSource.Factory<Int, Users>

    @Query("SELECT * FROM users WHERE blogId=null")
    fun getUsers(): LiveData<List<Users>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<Users>)
}