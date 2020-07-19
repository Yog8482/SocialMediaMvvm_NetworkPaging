package com.yogendra.socialmediamvvm.ui.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.yogendra.socialmediamvvm.data.Articles
import com.yogendra.socialmediamvvm.data.Result
import com.yogendra.socialmediamvvm.data.Users
import com.yogendra.socialmediamvvm.di.CoroutineScopeIO
import com.yogendra.socialmediamvvm.repository.ArticlesRepository
import com.yogendra.socialmediamvvm.repository.UsersRepository
import com.yogendra.socialmediamvvm.ui.article.ArticleFragment
import com.yogendra.socialmediamvvm.utils.IS_INTERNET_AVAILABLE
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

/**
 * The ViewModel for [UsersFragment].
 */
class UsersViewModel @Inject constructor(
    private val repository: UsersRepository,
    @CoroutineScopeIO private val ioCoroutineScope: CoroutineScope
) : ViewModel() {

    lateinit var refreshUsers:LiveData<Result<List<Users>>>
    val users: LiveData<PagedList<Users>>
        get() = _users
    private var _users = repository.observePagedSets(
        IS_INTERNET_AVAILABLE, ioCoroutineScope
    )


    fun refresh() {
         refreshUsers= repository.observeUsers( )
    }

}