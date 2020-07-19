package com.yogendra.socialmediamvvm.di

import com.google.gson.Gson
import com.yogendra.socialmediamvvm.api.NetworkConnectionInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient

import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Dagger module to provide core data functionality.
 */
@Module
class CoreDataModule {

    @Provides
    fun provideOkHttpClient(interceptor: NetworkConnectionInterceptor): OkHttpClient =
            OkHttpClient.Builder()
                    .addNetworkInterceptor(interceptor)
                    .build()

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()

    @Provides
    @Singleton
    fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory =
            GsonConverterFactory.create(gson)
}
