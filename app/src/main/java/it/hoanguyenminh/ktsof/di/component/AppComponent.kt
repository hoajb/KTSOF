package it.hoanguyenminh.ktsof.di.component

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import it.hoanguyenminh.ktsof.application.SOFApplication
import it.hoanguyenminh.ktsof.di.modules.AppModule
import it.hoanguyenminh.ktsof.di.modules.BuildersModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        BuildersModule::class,
        AppModule::class]
)

interface AppComponent : AndroidInjector<SOFApplication> {


    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(app: Application): Builder

//        fun networkingModule(module: NetworkModule): AppComponent.Builder

        fun build(): AppComponent
    }
}