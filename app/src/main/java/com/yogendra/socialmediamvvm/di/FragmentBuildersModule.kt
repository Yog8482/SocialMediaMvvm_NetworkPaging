package com.yogendra.socialmediamvvm.di


import com.yogendra.socialmediamvvm.ui.article.ArticleFragment
import com.yogendra.socialmediamvvm.ui.profile.ProfileFragment
import com.yogendra.socialmediamvvm.ui.users.UsersFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeArticleFragment(): ArticleFragment

    @ContributesAndroidInjector
    abstract fun contributeUsersFragment(): UsersFragment

    @ContributesAndroidInjector
    abstract fun contributeProfileFragment(): ProfileFragment
}
