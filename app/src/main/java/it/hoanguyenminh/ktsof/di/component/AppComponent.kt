package it.hoanguyenminh.ktsof.di.component

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import it.hoanguyenminh.ktsof.application.SOFApplication
import it.hoanguyenminh.ktsof.di.modules.BuildersModule
import it.hoanguyenminh.ktsof.di.modules.RepositoryModule

@Component(
    modules = [
        AndroidSupportInjectionModule::class,
//        AppModule::class,
        BuildersModule::class,
        RepositoryModule::class
    ]
)

interface AppComponent : AndroidInjector<SOFApplication> {

    //    @Component.Builder
//    abstract class Builder : AndroidInjector.Builder<SOFApplication>()
//
    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(app: SOFApplication): AppComponent.Builder

//        fun repositoryModule(module: RepositoryModule): AppComponent.Builder

        fun build(): AppComponent
    }
}