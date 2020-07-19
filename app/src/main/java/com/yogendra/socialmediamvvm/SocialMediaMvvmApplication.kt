package com.yogendra.socialmediamvvm

import android.app.Activity
import android.app.Application
import com.yogendra.socialmediamvvm.di.AppInjector
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class SocialMediaMvvmApplication : Application(), HasAndroidInjector {
    //HasActivityInjector
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()

        AppInjector.init(this)
    }

    //    override fun activityInjector() = dispatchingAndroidInjector
    override fun androidInjector(): AndroidInjector<Any> =
        dispatchingAndroidInjector as AndroidInjector<Any>


}