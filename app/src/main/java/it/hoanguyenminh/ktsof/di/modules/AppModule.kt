package it.hoanguyenminh.ktsof.di.modules

import android.app.Application
import dagger.Module
import dagger.Provides

@Module
class AppModule {

    @Provides
    fun provideApplication(app: Application): Application = app
}