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
import com.yogendra.socialmediamvvm.utils.IS_INTERNET_AVAILABLE
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import javax.inject.Inject

class UsersViewModel @Inject constructor(
    private val repository: UsersRepository,
    @CoroutineScopeIO private val ioCoroutineScope: CoroutineScope
) : ViewModel() {


    val users: LiveData<PagedList<Users>>
        get() = _users
    private var _users = repository.observePagedSets(
        IS_INTERNET_AVAILABLE, ioCoroutineScope
    )


    fun refresh() = repository.observeUsers()


    /**
     * Cancel all coroutines when the ViewModel is cleared.
     */
    override fun onCleared() {
        super.onCleared()
        ioCoroutineScope.cancel()
    }
}