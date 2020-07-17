package com.yogendra.socialmediamvvm.utils

import java.io.IOException

class NoConnectivityException : IOException() {
    override val message: String?
        get() = "No Internet Connection"
}