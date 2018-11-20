package it.hoanguyenminh.ktsof.di.modules

import android.app.Application
import dagger.Module
import dagger.Provides
import it.hoanguyenminh.ktsof.application.SOFApplication
import javax.inject.Singleton

@Module
class AppModule {
//no use anymore
//    @Provides
//    fun provideSOFAPI(retrofit: Retrofit): SOFApi = retrofit.create(SOFApi.class)

    @Provides
    @Singleton
    fun provideApplication(application: SOFApplication): Application = application
}