package it.hoanguyenminh.ktsof.di.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import it.hoanguyenminh.ktsof.MainActivity

@Module
abstract class BuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity
}