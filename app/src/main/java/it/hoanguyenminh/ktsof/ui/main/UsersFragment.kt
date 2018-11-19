package it.hoanguyenminh.ktsof.ui.main

import android.os.Bundle
import it.hoanguyenminh.ktsof.application.SOFApplication
import it.hoanguyenminh.ktsof.base.recyclerview.BaseRecycleViewAdapter
import it.hoanguyenminh.ktsof.base.recyclerview.ItemClickListener
import it.hoanguyenminh.ktsof.base.view.BaseListFragment
import it.hoanguyenminh.ktsof.repository.RepositoryLiveData
import it.hoanguyenminh.ktsof.repository.data.User
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

    lateinit var factory: UsersViewModel.Factory

    override fun createAdapter(
        list: ArrayList<User>,
        clickListener: ItemClickListener<User>?
    ): BaseRecycleViewAdapter<User> = UsersAdapter(mListData = list, clickListener = clickListener)

    override val mTAG: String?
        get() = UsersFragment::class.java.simpleName

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

//        Timber.d("Application" + application.toString())
//        Timber.d("Application" + repositoryLiveData.toString())

//        factory = UsersViewModel.Factory(application, repositoryLiveData)
//        var usersViewModel: UsersViewModel = ViewModelProviders.of(this, factory).get(UsersViewModel::class.java)
//        usersViewModel.data.observe(this,
//            Observer<ArrayList<User>> { users ->
//                Timber.d(mTAG, "users: %s", users?.get(0))
//                Toast.makeText(activity, "users: " + users?.get(0), Toast.LENGTH_LONG).show()
//            })
    }

}