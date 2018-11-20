package it.hoanguyenminh.ktsof.ui.main

import android.app.Application
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import it.hoanguyenminh.ktsof.application.SOFApplication
import it.hoanguyenminh.ktsof.repository.RepositoryLiveData
import it.hoanguyenminh.ktsof.repository.data.User
import it.hoanguyenminh.ktsof.viewmodel.BaseViewModel
import javax.inject.Inject

/**
 * Created by Hoa Nguyen on 2018 November 17.
 * hoa.nguyenminh.it@gmail.com
 */

open class UsersViewModel(application: Application, private val repository: RepositoryLiveData) :
    BaseViewModel(application), LifecycleObserver {

    lateinit var data: LiveData<ArrayList<User>>

//    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
//    open fun onCreate() {
//        data = repository.getUsersFromApi(0)
//    }

    fun getUsers(page: Int) {
        data = repository.getUsersFromApi(page)
    }
}

class UsersViewModelFactory @Inject constructor(
    private val application: SOFApplication,
    private val repository: RepositoryLiveData
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UsersViewModel(application, repository) as T
    }
}
