package com.yogendra.socialmediamvvm.utils


//Retrofit endpoint
const val BASE_URL = "https://5e99a9b1bc561b0016af3540.mockapi.io/jet2/api/v1/"


const val DEFAULT_URL_PARAMETRES_ARTICLE = "blogs?page=1&limit=10"
const val URL_PARAMETRES_USERS = "users?page=1&limit=10"


/**
 * Contains all possible outcomes that can appear when a network operation takes place.
 * By the way, the state called 'ERROR' means that there is a problem on the server, or some other issue.
 */
enum class QueryState {
    PROCESS,
    NO_NETWORK,
    ERROR,
    DONE
}

const val DATABASE_NAME = "SociaMediaMvvmDB"
