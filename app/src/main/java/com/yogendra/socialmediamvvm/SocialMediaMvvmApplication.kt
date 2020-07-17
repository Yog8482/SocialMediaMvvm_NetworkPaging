package com.yogendra.socialmediamvvm

import android.app.Application

class SocialMediaMvvmApplication : Application() {//HasActivityInjector
//    @Inject
//    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()

//        AppInjector.init(this)
    }

//    override fun activityInjector() = dispatchingAndroidInjector

}