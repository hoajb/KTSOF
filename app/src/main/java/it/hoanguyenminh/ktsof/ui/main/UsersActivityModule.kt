package it.hoanguyenminh.ktsof.ui.main

import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by Hoa Nguyen on 2018 November 07.
 * hoa.nguyenminh.it@gmail.com
 */

@Module
abstract class UsersActivityModule {

    @ContributesAndroidInjector()
    abstract fun bindUsersFragment(): UsersFragment
}