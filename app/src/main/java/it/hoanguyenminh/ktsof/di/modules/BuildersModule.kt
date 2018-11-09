package it.hoanguyenminh.ktsof.di.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import it.hoanguyenminh.ktsof.view.main.MainActivity
import it.hoanguyenminh.ktsof.view.main.MainActivityModule

@Module
abstract class BuildersModule {

    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun bindMainActivity(): MainActivity
}