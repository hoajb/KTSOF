package it.hoanguyenminh.ktsof.di.component

import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import it.hoanguyenminh.ktsof.application.SOFApplication
import it.hoanguyenminh.ktsof.di.modules.AppModule
import it.hoanguyenminh.ktsof.di.modules.BuildersModule

@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        BuildersModule::class,
        AppModule::class]
)

interface AppComponent : AndroidInjector<SOFApplication> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<SOFApplication>()

//    @Component.Builder
//    interface Builder {
//
//        @BindsInstance
//        fun application(app: Application): Builder
//
////        fun networkingModule(module: NetworkModule): AppComponent.Builder
//
//        fun build(): AppComponent
//    }
}