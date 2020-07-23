package com.yogendra.socialmediamvvm.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yogendra.socialmediamvvm.data.Articles
import com.yogendra.socialmediamvvm.data.Result
import com.yogendra.socialmediamvvm.data.Users
import com.yogendra.socialmediamvvm.repository.UsersRepository
import javax.inject.Inject

class ProfileViewModel @Inject constructor(val repository: UsersRepository) : ViewModel() {

//    lateinit var userId: String

    val article_profile: LiveData<Result<Articles>>
        get() = _article_profile

    val user_profile: LiveData<Result<Users>>
        get() = _user_profile

    private var _article_profile: LiveData<Result<Articles>> = MutableLiveData()
    private  var _user_profile: LiveData<Result<Users>> = MutableLiveData()

    fun getArticle_profile(articleId: String) {
        _article_profile = repository.observeArticleProfile(articleId)
    }

    fun getUserProfile(userId: String) {
        _user_profile = repository.observeUserProfile(userId)
    }
}