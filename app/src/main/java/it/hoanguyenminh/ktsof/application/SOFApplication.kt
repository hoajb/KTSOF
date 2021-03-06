package it.hoanguyenminh.ktsof.application

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import it.hoanguyenminh.ktsof.di.component.DaggerAppComponent
import timber.log.Timber


class SOFApplication : DaggerApplication() {
    private val TAG = SOFApplication::class.java!!.getSimpleName()
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder()
            .application(this)
//            .repositoryModule(RepositoryModule())
            .build()
    }

    override fun onCreate() {
        super.onCreate()

        Timber.uprootAll()
        Timber.plant(Timber.DebugTree())

    }

//    @Inject
//    fun logInjection() {
//        Timber.i(TAG, "Injecting " + SOFApplication::class.java!!.getSimpleName())
//    }
}