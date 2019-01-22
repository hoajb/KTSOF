package it.hoanguyenminh.ktsof.ui.main

import android.os.Bundle
import android.view.View
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
    lateinit var factory: UsersViewModelFactory

    lateinit var usersViewModel: UsersViewModel

    override fun createAdapter(
        list: List<User>,
        clickListener: ItemClickListener<User>?
    ): BaseRecycleViewAdapter<User> = UsersAdapter(mListData = list, clickListener = clickListener)

    override val mTAG: String?
        get() = UsersFragment::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        usersViewModel = ViewModelProviders.of(this, factory).get(UsersViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        usersViewModel.getUsers(1)
        usersViewModel.data.observe(this,
            Observer<List<User>> { users ->
                Timber.d(mTAG, "users: %s", users?.get(0))
//                showToast("users: " + users?.get(0))
                setUpRecyclerView(users)
            })
    }
}