package it.hoanguyenminh.ktsof.application

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import timber.log.Timber

class SOFApplication : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder()
            .application(this)
//            .networkingModule(NetworkModule())
            .build()
    }

    override fun onCreate() {
        super.onCreate()



        Timber.uprootAll()
        Timber.plant(Timber.DebugTree())

    }
}