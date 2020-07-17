package com.yogendra.socialmediamvvm.ui.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UsersViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is users Fragment"
    }
    val text: LiveData<String> = _text
    var userID: Int = 1 //selected user
}