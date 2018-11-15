package it.hoanguyenminh.ktsof.di.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import it.hoanguyenminh.ktsof.view.main.MainActivity
import it.hoanguyenminh.ktsof.view.main.UsersActivityModule

@Module
abstract class BuildersModule {

    @ContributesAndroidInjector(modules = [UsersActivityModule::class])
    abstract fun bindMainActivity(): MainActivity

//    @ContributesAndroidInjector()
//    abstract fun bindMainActivity(): MainActivity

//    @ContributesAndroidInjector()
//    abstract fun bindUsersFragment(): UsersFragment
}