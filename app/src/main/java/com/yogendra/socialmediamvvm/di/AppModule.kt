package com.yogendra.socialmediamvvm.di

import android.app.Application
import com.yogendra.socialmediamvvm.api.FetchDataService
import com.yogendra.socialmediamvvm.database.AppDatabase
import com.yogendra.socialmediamvvm.datasource.remote.ArticlesRemoteDataSource
import com.yogendra.socialmediamvvm.datasource.remote.UsersRemoteDataSource
import com.yogendra.socialmediamvvm.utils.BASE_URL

import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class, CoreDataModule::class])
class AppModule {

    @Singleton
    @Provides
    fun provideFetchDataService(
        @SocialMediaMvvmAPI okhttpClient: OkHttpClient,
        converterFactory: GsonConverterFactory
    ) = provideService(okhttpClient, converterFactory, FetchDataService::class.java)

    @Singleton
    @Provides
    fun provideArticlesRemoteDataSource(fetchDataService: FetchDataService) =
        ArticlesRemoteDataSource(fetchDataService)

    @Singleton
    @Provides
    fun provideUsersRemoteDataSource(fetchDataService: FetchDataService) =
        UsersRemoteDataSource(fetchDataService)

}

@Singleton
@Provides
fun provideDb(app: Application) = AppDatabase.getInstance(app)

@Singleton
@Provides
fun provideArticlesDao(db: AppDatabase) = db.articlesDao()


@Singleton
@Provides
fun provideUsersDao(db: AppDatabase) = db.usersDao()

@CoroutineScopeIO
@Provides
fun provideCoroutineScopeIO() = CoroutineScope(Dispatchers.IO)


private fun createRetrofit(
    okhttpClient: OkHttpClient,
    converterFactory: GsonConverterFactory
): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okhttpClient)
        .addConverterFactory(converterFactory)
        .build()
}

private fun <T> provideService(
    okhttpClient: OkHttpClient,
    converterFactory: GsonConverterFactory, clazz: Class<T>
): T {
    return createRetrofit(okhttpClient, converterFactory).create(clazz)
}

