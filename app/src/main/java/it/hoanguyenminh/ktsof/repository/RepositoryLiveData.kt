package it.hoanguyenminh.ktsof.repository

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.toLiveData
import it.hoanguyenminh.ktsof.repository.config.Config
import it.hoanguyenminh.ktsof.repository.data.User
import it.hoanguyenminh.ktsof.repository.data.Users
import it.hoanguyenminh.ktsof.repository.local.UserDao
import it.hoanguyenminh.ktsof.repository.paging.UsersDataSourceFactory
import it.hoanguyenminh.ktsof.repository.remote.SOFApiCall
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

class RepositoryLiveData @Inject constructor(val sofApi: SOFApiCall) {
    val userDao: UserDao? = null

    val livedata: MutableLiveData<List<User>> = MutableLiveData()

    val livedataListing: MutableLiveData<Listing<User>> = MutableLiveData()

//    fun getUsers(): Observable<List<User>> {
//        return Observable.concatArray(
//            getUsersFromDb(),
//            getUsersFromApi(1)
//        )
//    }


//    fun getUsersFromDb(): Observable<List<User>> {
//        return userDao.getUsers().filter { it.isNotEmpty() }
//            .toObservable()
//            .doOnNext {
//                Timber.d("Dispatching ${it.size} users from DB...")
//            }
//    }

    fun getUsersFromApi(page: Int): LiveData<List<User>> {
//        return sofApi.getUsers(Config.SITE, Config.PAGE_SIZE, page)
//            .doOnNext {
//                Timber.d("Dispatching ${it.size} users from API...")
//                storeUsersInDb(it)
//            }

        sofApi.getUsers(Config.SITE, Config.PAGE_SIZE, page)
            .enqueue(object : Callback<Users> {
                override fun onFailure(call: Call<Users>, t: Throwable) {
                    livedata.postValue(null)
                }

                override fun onResponse(call: Call<Users>, response: Response<Users>) {
                    if (response.isSuccessful) {
                        val users = response.body()
                        storeUsersInDb(users?.items)
                        livedata.postValue(users?.items)
                    } else {
                        livedata.postValue(null)
                    }
                }
            })

        return livedata
    }

    fun storeUsersInDb(users: List<User>?) {
//        Observable.fromCallable { userDao.insertAll(users) }
//            .subscribeOn(Schedulers.io())
//            .observeOn(Schedulers.io())
//            .subscribe {
//                Timber.d("Inserted ${users.size} users from API in DB...")
//            }

        Timber.d("Inserted ${users?.size} users from API in DB...")

    }

    @MainThread
    fun getUsersPagedFromAPI(page: Int): MutableLiveData<Listing<User>> {
        val sourceFactory = UsersDataSourceFactory(sofApi)

        // We use toLiveData Kotlin extension function here, you could also use LivePagedListBuilder
        val livePagedList = sourceFactory.toLiveData(
            pageSize = Config.PAGE_SIZE
//            ,
//            // provide custom executor for network requests, otherwise it will default to
//            // Arch Components' IO pool which is also used for disk access
//            fetchExecutor = networkExecutor
        )

        val refreshState = Transformations.switchMap(sourceFactory.sourceLiveData) {
            it.initialLoad
        }

        val listing = Listing(
            pagedList = livePagedList,
            networkState = Transformations.switchMap(sourceFactory.sourceLiveData) {
                it.networkState
            },
            retry = {
                sourceFactory.sourceLiveData.value?.retryAllFailed()
            },
            refresh = {
                sourceFactory.sourceLiveData.value?.invalidate()
            },
            refreshState = refreshState
        )

        livedataListing.postValue(listing)

        return livedataListing
    }

}