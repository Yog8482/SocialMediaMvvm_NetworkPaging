package com.yogendra.socialmediamvvm.ui.article

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.yogendra.socialmediamvvm.data.Articles
import com.yogendra.socialmediamvvm.di.CoroutineScopeIO
import com.yogendra.socialmediamvvm.repository.ArticlesRepository
import com.yogendra.socialmediamvvm.utils.IS_INTERNET_AVAILABLE
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import javax.inject.Inject


/**
 * The ViewModel for [ArticleFragment].
 */
class ArticleViewModel @Inject constructor(
    private val repository: ArticlesRepository,
    @CoroutineScopeIO private val ioCoroutineScope: CoroutineScope
) : ViewModel() {

    val articles: LiveData<PagedList<Articles>>
        get() = _articles
    private var _articles = repository.observePagedSets(
        IS_INTERNET_AVAILABLE, ioCoroutineScope
    )


    fun refresh() {
        _articles = repository.observePagedSets(
            IS_INTERNET_AVAILABLE, ioCoroutineScope
        )
    }

    /**
     * Cancel all coroutines when the ViewModel is cleared.
     */
    override fun onCleared() {
        super.onCleared()
        ioCoroutineScope.cancel()
    }
}