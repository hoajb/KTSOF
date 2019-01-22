package it.hoanguyenminh.ktsof.repository

import androidx.annotation.MainThread
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.toLiveData
import io.reactivex.disposables.CompositeDisposable
import it.hoanguyenminh.ktsof.repository.config.Config
import it.hoanguyenminh.ktsof.repository.data.User
import it.hoanguyenminh.ktsof.repository.local.UserDao
import it.hoanguyenminh.ktsof.repository.paging.UsersDataSourceRxFactory
import it.hoanguyenminh.ktsof.repository.remote.SOFApi

class RepositoryRxJava(val sofApi: SOFApi, val userDao: UserDao? = null) {

    val livedata: MutableLiveData<List<User>> = MutableLiveData()


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
//

    @MainThread
    fun getUsersPagedFromAPI(
        compositeDisposable: CompositeDisposable,
        repoResult: MutableLiveData<Listing<User>>
    ): MutableLiveData<Listing<User>> {
        val sourceFactory = UsersDataSourceRxFactory(sofApi, compositeDisposable)

        // We use toLiveData Kotlin extension function here, you could also use LivePagedListBuilder
        val livePagedList = sourceFactory.toLiveData(
            pageSize = Config.PAGE_SIZE,
            initialLoadKey = 1
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

        repoResult.postValue(listing)

        return repoResult
    }

//    fun storeUsersInDb(users: List<User>) {
//        Observable.fromCallable { userDao.insertAll(users) }
//            .subscribeOn(Schedulers.io())
//            .observeOn(Schedulers.io())
//            .subscribe {
//                Timber.d("Inserted ${users.size} users from API in DB...")
//            }
//    }

}