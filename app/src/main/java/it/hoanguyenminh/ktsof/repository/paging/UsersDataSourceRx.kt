package it.hoanguyenminh.ktsof.repository.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import it.hoanguyenminh.ktsof.repository.NetworkState
import it.hoanguyenminh.ktsof.repository.config.Config
import it.hoanguyenminh.ktsof.repository.data.User
import it.hoanguyenminh.ktsof.repository.data.Users
import it.hoanguyenminh.ktsof.repository.remote.SOFApi

/**
 * Created by Hoa Nguyen on 2018 November 21.
 * hoa.nguyenminh.it@gmail.com
 */

class UsersDataSourceRx(
    private val sofApi: SOFApi,
    private val compositeDisposable: CompositeDisposable
) : PageKeyedDataSource<Int, User>() {

    private var retry: (() -> Any)? = null

    /**
     * There is no sync on the state because paging will always call loadInitial first then wait
     * for it to return some success value before calling loadAfter.
     */
    val networkState = MutableLiveData<NetworkState>()

    val initialLoad = MutableLiveData<NetworkState>()

    fun retryAllFailed() {
        val prevRetry = retry
        retry = null
//        prevRetry?.let {
//            retryExecutor.execute {
//                it.invoke()
//            }
//        }

        prevRetry?.invoke()
    }

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, User>) {
        val initPage = 1

        networkState.postValue(NetworkState.LOADING)
        initialLoad.postValue(NetworkState.LOADING)

        val request = sofApi.getUsers(
            site = Config.SITE,
            pagesize = params.requestedLoadSize,
            page = initPage
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ usersObject: Users ->
                retry = null
                networkState.postValue(NetworkState.LOADED)
                initialLoad.postValue(NetworkState.LOADED)
                callback.onResult(
                    usersObject.items ?: emptyList(),
                    null,
                    initPage + 1
                )
            }, { throwable: Throwable? ->
                retry = {
                    loadInitial(params, callback)
                }
                val error = NetworkState.error(throwable?.message ?: "unknown error")
                networkState.postValue(error)
                initialLoad.postValue(error)
            })

        compositeDisposable.add(request)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, User>) {
        // ignored, since we only ever append to our initial load
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, User>) {
        networkState.postValue(NetworkState.LOADING)

        val request = sofApi.getUsers(
            site = Config.SITE,
            pagesize = params.requestedLoadSize,
            page = params.key
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ usersObject: Users ->
                retry = null
                callback.onResult(
                    usersObject.items ?: emptyList(),
                    params.key + 1
                )
                networkState.postValue(NetworkState.LOADED)
            }, { throwable: Throwable? ->
                retry = {
                    loadAfter(params, callback)
                }
                val error = NetworkState.error(throwable?.message ?: "unknown error")
                networkState.postValue(error)
            })

        compositeDisposable.add(request)
    }
}

class UsersDataSourceRxFactory(
    private val sofApiCall: SOFApi,
    private val compositeDisposable: CompositeDisposable
) : DataSource.Factory<Int, User>() {
    val sourceLiveData = MutableLiveData<UsersDataSourceRx>()
    override fun create(): DataSource<Int, User> {
        val source = UsersDataSourceRx(sofApiCall, compositeDisposable)

        sourceLiveData.postValue(source)
        return source
    }
}

