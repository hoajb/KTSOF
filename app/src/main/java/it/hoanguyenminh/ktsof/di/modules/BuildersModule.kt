package it.hoanguyenminh.ktsof.di.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import it.hoanguyenminh.ktsof.ui.main.MainActivity
import it.hoanguyenminh.ktsof.ui.main.UsersFragment
import it.hoanguyenminh.ktsof.ui.main.UsersPagedFragment

@Module
abstract class BuildersModule {

//    @ContributesAndroidInjector(modules = [UsersActivityModule::class])
//    abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector()
    abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector()
    abstract fun bindUsersFragment(): UsersFragment

    @ContributesAndroidInjector()
    abstract fun bindUsersPagedFragment(): UsersPagedFragment
}