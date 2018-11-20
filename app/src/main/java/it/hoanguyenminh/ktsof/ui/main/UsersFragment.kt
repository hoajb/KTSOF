package it.hoanguyenminh.ktsof.ui.main

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import it.hoanguyenminh.ktsof.application.SOFApplication
import it.hoanguyenminh.ktsof.base.recyclerview.BaseRecycleViewAdapter
import it.hoanguyenminh.ktsof.base.recyclerview.ItemClickListener
import it.hoanguyenminh.ktsof.base.view.BaseListFragment
import it.hoanguyenminh.ktsof.repository.RepositoryLiveData
import it.hoanguyenminh.ktsof.repository.data.User
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Hoa Nguyen on 2018 November 13.
 * hoa.nguyenminh.it@gmail.com
 */

class UsersFragment : BaseListFragment<User>() {

    @Inject
    lateinit var application: SOFApplication

    @Inject
    lateinit var repositoryLiveData: RepositoryLiveData

    @Inject
    lateinit var factory: UsersViewModelFactory

    override fun createAdapter(
        list: ArrayList<User>,
        clickListener: ItemClickListener<User>?
    ): BaseRecycleViewAdapter<User> = UsersAdapter(mListData = list, clickListener = clickListener)

    override val mTAG: String?
        get() = UsersFragment::class.java.simpleName

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        Timber.d("HOAA" + application.toString())
        Timber.d("HOAA" + repositoryLiveData.toString())

//        factory = UsersViewModelFactory(application, repositoryLiveData)

        Timber.d("HOAA" + factory.toString())
        var usersViewModel: UsersViewModel = ViewModelProviders.of(this, factory).get(UsersViewModel::class.java)
        usersViewModel.getUsers(1)
        usersViewModel.data.observe(this,
            Observer<ArrayList<User>> { users ->
                Timber.d(mTAG, "users: %s", users?.get(0))
                showToast("users: " + users?.get(0))
            })

    }

}