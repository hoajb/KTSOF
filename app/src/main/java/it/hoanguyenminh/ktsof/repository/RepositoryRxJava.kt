package it.hoanguyenminh.ktsof.repository

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import it.hoanguyenminh.ktsof.repository.config.Config
import it.hoanguyenminh.ktsof.repository.data.User
import it.hoanguyenminh.ktsof.repository.local.UserDao
import it.hoanguyenminh.ktsof.repository.remote.SOFApi
import timber.log.Timber

class RepositoryRxJava(val sofApi: SOFApi, val userDao: UserDao) {

    fun getUsers(): Observable<List<User>> {
        return Observable.concatArray(
            getUsersFromDb(),
            getUsersFromApi(1)
        )
    }


    fun getUsersFromDb(): Observable<List<User>> {
        return userDao.getUsers().filter { it.isNotEmpty() }
            .toObservable()
            .doOnNext {
                Timber.d("Dispatching ${it.size} users from DB...")
            }
    }

    fun getUsersFromApi(page: Int): Observable<List<User>> {
        return sofApi.getUsers(Config.SITE, Config.PAGE_SIZE, page)
            .doOnNext {
                Timber.d("Dispatching ${it.size} users from API...")
                storeUsersInDb(it)
            }
    }

    fun storeUsersInDb(users: List<User>) {
        Observable.fromCallable { userDao.insertAll(users) }
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe {
                Timber.d("Inserted ${users.size} users from API in DB...")
            }
    }

}