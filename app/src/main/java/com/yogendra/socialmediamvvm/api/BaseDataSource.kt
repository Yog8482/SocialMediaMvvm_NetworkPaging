package com.yogendra.socialmediamvvm.api

import retrofit2.Response
import com.yogendra.socialmediamvvm.data.*
import com.yogendra.socialmediamvvm.utils.NoConnectivityException

/**
 * Abstract Base Data source class with error handling
 */
abstract class BaseDataSource {

    protected suspend fun <T> getResult(call: suspend () -> Response<T>): Result<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return Result.success(body)
            }
            return error(" ${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(message: String): Result<T> {
        if (message.contains("Unable to resolve host \"5e99a9b1bc561b0016af3540.mockapi.io\": No address associated with hostname")) {
            return Result.error(NoConnectivityException().message.toString())
        }
        return Result.error(message)
    }

}

