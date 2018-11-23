package it.hoanguyenminh.ktsof.repository.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import it.hoanguyenminh.ktsof.repository.NetworkState
import it.hoanguyenminh.ktsof.repository.config.Config
import it.hoanguyenminh.ktsof.repository.data.User
import it.hoanguyenminh.ktsof.repository.data.Users
import it.hoanguyenminh.ktsof.repository.remote.SOFApiCall
import retrofit2.Call
import retrofit2.Response
import java.io.IOException

/**
 * Created by Hoa Nguyen on 2018 November 21.
 * hoa.nguyenminh.it@gmail.com
 */

class UsersDataSource(private val sofApi: SOFApiCall) : PageKeyedDataSource<Int, User>() {

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
        val request = sofApi.getUsers(
            site = Config.SITE,
//            pagesize = params.requestedLoadSize,
            pagesize = Config.PAGE_SIZE,
            page = initPage
        )

        networkState.postValue(NetworkState.LOADING)
        initialLoad.postValue(NetworkState.LOADING)

        try {
            val response = request.execute()
            val items = response.body()?.items ?: emptyList()
            retry = null
            networkState.postValue(NetworkState.LOADED)
            initialLoad.postValue(NetworkState.LOADED)
            callback.onResult(
                items,
                initPage - 1,
                initPage + 1
            )
        } catch (ioException: IOException) {
            retry = {
                loadInitial(params, callback)
            }
            val error = NetworkState.error(ioException.message ?: "unknown error")
            networkState.postValue(error)
            initialLoad.postValue(error)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, User>) {
        // ignored, since we only ever append to our initial load
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, User>) {
        networkState.postValue(NetworkState.LOADING)
        sofApi.getUsers(
            site = it.hoanguyenminh.ktsof.repository.config.Config.SITE,
            pagesize = params.requestedLoadSize,
            page = params.key
        )
            .enqueue(object : retrofit2.Callback<Users> {
                override fun onFailure(call: Call<Users>, t: Throwable) {
                    retry = {
                        loadAfter(params, callback)
                    }
                    networkState.postValue(NetworkState.error(t.message ?: "unknown err"))
                }

                override fun onResponse(
                    call: Call<Users>,
                    response: Response<Users>
                ) {
                    if (response.isSuccessful) {
                        val items = response.body()?.items ?: emptyList()
                        retry = null
                        callback.onResult(items, params.key + 1)
                        networkState.postValue(NetworkState.LOADED)
                    } else {
                        retry = {
                            loadAfter(params, callback)
                        }
                        networkState.postValue(
                            NetworkState.error("error code: ${response.code()}")
                        )
                    }
                }
            }
            )
    }
}

