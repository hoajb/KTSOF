package it.hoanguyenminh.ktsof.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import it.hoanguyenminh.ktsof.repository.config.Config
import it.hoanguyenminh.ktsof.repository.data.User
import it.hoanguyenminh.ktsof.repository.data.Users
import it.hoanguyenminh.ktsof.repository.local.UserDao
import it.hoanguyenminh.ktsof.repository.remote.SOFApiCall
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

class RepositoryLiveData @Inject constructor(val sofApi: SOFApiCall) {
    val userDao: UserDao? = null

    val livedata: MutableLiveData<ArrayList<User>> = MutableLiveData()

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

    fun getUsersFromApi(page: Int): LiveData<ArrayList<User>> {
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

    fun storeUsersInDb(users: ArrayList<User>?) {
//        Observable.fromCallable { userDao.insertAll(users) }
//            .subscribeOn(Schedulers.io())
//            .observeOn(Schedulers.io())
//            .subscribe {
//                Timber.d("Inserted ${users.size} users from API in DB...")
//            }

        Timber.d("Inserted ${users?.size} users from API in DB...")

    }

}