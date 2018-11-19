package it.hoanguyenminh.ktsof.di.modules

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import it.hoanguyenminh.ktsof.repository.RepositoryLiveData
import it.hoanguyenminh.ktsof.repository.config.Config
import it.hoanguyenminh.ktsof.repository.remote.SOFApi
import it.hoanguyenminh.ktsof.repository.remote.SOFApiCall
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Hoa Nguyen on 2018 November 13.
 * hoa.nguyenminh.it@gmail.com
 */

@Module
class RepositoryModule {

    @Provides
    fun provideGson(): Gson {
        return GsonBuilder()
            .serializeNulls()
            .create()
    }

    @Provides
    fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory {
        return GsonConverterFactory.create(gson)
    }

//    @Provides
//    fun provideCallAdapter(): CallAdapter.Factory {
//        return CallAdapter()
//    }

    @Provides
    fun provideClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY


        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    @Provides
    fun provideRetrofit(
        client: OkHttpClient,
        converter: GsonConverterFactory
//        ,
//        adapter: CallAdapter.Factory
    ): SOFApi {
        val buidler = Retrofit.Builder()
            .baseUrl(Config.BASE_URL)
            .client(client)
            .addConverterFactory(converter)
//            .addCallAdapterFactory(adapter)
            .build()

        return buidler.create(SOFApi::class.java)
    }

    @Provides
    fun provideRetrofitCall(
        client: OkHttpClient,
        converter: GsonConverterFactory,
        adapter: CallAdapter.Factory
    ): SOFApiCall {
        val buidler = Retrofit.Builder()
            .baseUrl(Config.BASE_URL)
            .client(client)
            .addConverterFactory(converter)
            .addCallAdapterFactory(adapter)
            .build()

        return buidler.create(SOFApiCall::class.java)
    }

//    @Provides
//    fun providesUserDao(): UserDao {
//
//        return UserDao()
//    }

    @Provides
    fun provideRepositoryLiveData(
        sofApiCall: SOFApiCall
    ): RepositoryLiveData {


        return RepositoryLiveData(sofApiCall)
    }
}