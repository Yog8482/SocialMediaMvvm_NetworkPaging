package com.yogendra.socialmediamvvm.api

import com.yogendra.socialmediamvvm.utils.IS_INTERNET_AVAILABLE
import com.yogendra.socialmediamvvm.utils.NoConnectivityException
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response


 class NetworkConnectionInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!IS_INTERNET_AVAILABLE) {
            throw NoConnectivityException()
            // Throwing our custom exception 'NoConnectivityException'
        }
        val builder: Request.Builder = chain.request().newBuilder()
        return chain.proceed(builder.build())
    }


}