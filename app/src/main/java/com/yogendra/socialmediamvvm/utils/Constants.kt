package com.yogendra.socialmediamvvm.utils


//Retrofit endpoint
const val BASE_URL = "https://5e99a9b1bc561b0016af3540.mockapi.io/jet2/api/v1/"


const val DEFAULT_URL_PARAMETRES_ARTICLE = "blogs"
const val URL_PARAMETRES_USERS = "users"


const val DATABASE_NAME = "socialmediamvvm-db"
var IS_INTERNET_AVAILABLE = false

enum class ProgressStatus {
    LOADING,
    NO_NETWORK,
    ERROR,
    COMPLTED
}