package it.hoanguyenminh.ktsof.ui.main

import android.app.Application
import androidx.lifecycle.*
import androidx.lifecycle.Transformations.switchMap
import io.reactivex.disposables.CompositeDisposable
import it.hoanguyenminh.ktsof.application.SOFApplication
import it.hoanguyenminh.ktsof.repository.Listing
import it.hoanguyenminh.ktsof.repository.RepositoryRxJava
import it.hoanguyenminh.ktsof.repository.data.User
import it.hoanguyenminh.ktsof.viewmodel.BaseViewModel
import javax.inject.Inject

/**
 * Created by Hoa Nguyen on 2018 November 17.
 * hoa.nguyenminh.it@gmail.com
 */

open class UsersViewModel(application: Application, private val repository: RepositoryRxJava) :
    BaseViewModel(application), LifecycleObserver {

//    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
//    open fun onCreate() {
//        data = repository.getUsersFromApi(0)
//    }

    lateinit var data: LiveData<List<User>>
    val compositeDisposable: CompositeDisposable = CompositeDisposable()
    val repoResult: MutableLiveData<Listing<User>> = MutableLiveData()


    val users = switchMap(repoResult) { it.pagedList }!!
    val networkState = switchMap(repoResult) { it.networkState }!!
    val refreshState = switchMap(repoResult) { it.refreshState }!!

//    fun getUsers(page: Int) {
////        data = repository.getUsersFromApi(page)
////    }

    fun loadUsers() {
        repository.getUsersPagedFromAPI(compositeDisposable, repoResult)
    }

    fun refresh() {
        repoResult.value?.refresh?.invoke()
    }

    fun retry() {
        val listing = repoResult.value
        listing?.retry?.invoke()
    }

//    fun getUsersPaged(page: Int): Boolean {
//        if (currentPage.value == page) {
//            return false
//        }
//
//        currentPage.value = page
//        return true
//    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}


class UsersViewModelFactory @Inject constructor(
    private val application: SOFApplication,
    private val repository: RepositoryRxJava
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return UsersViewModel(application, repository) as T
    }
}
