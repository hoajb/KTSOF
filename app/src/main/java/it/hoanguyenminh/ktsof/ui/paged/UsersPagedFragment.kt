package it.hoanguyenminh.ktsof.ui.paged

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import it.hoanguyenminh.ktsof.application.SOFApplication
import it.hoanguyenminh.ktsof.base.recyclerview.BasePagedListAdapter
import it.hoanguyenminh.ktsof.base.recyclerview.ItemClickListener
import it.hoanguyenminh.ktsof.base.view.BasePagedListFragment
import it.hoanguyenminh.ktsof.repository.NetworkState.Companion.LOADING
import it.hoanguyenminh.ktsof.repository.RepositoryLiveData
import it.hoanguyenminh.ktsof.repository.data.User
import it.hoanguyenminh.ktsof.ui.main.UsersViewHolder
import it.hoanguyenminh.ktsof.ui.main.UsersViewModel
import it.hoanguyenminh.ktsof.ui.main.UsersViewModelFactory
import kotlinx.android.synthetic.main.base_fragment_single_list.*
import javax.inject.Inject

/**
 * Created by Hoa Nguyen on 2018 November 13.
 * hoa.nguyenminh.it@gmail.com
 */

class UsersPagedFragment : BasePagedListFragment<User, UsersViewHolder>() {
    override fun createAdapter(): BasePagedListAdapter<User, UsersViewHolder> =
        UsersPagedAdapter(
            retryCallback = { usersViewModel.retry() },
            clickListener = object : ItemClickListener<User> {
                override fun onItemClick(data: User?, position: Int, typeClick: Int) {
                    data?.display_name?.let { showToast(it) }
                }
            })

    @Inject
    lateinit var application: SOFApplication

    @Inject
    lateinit var repositoryLiveData: RepositoryLiveData

    @Inject
    lateinit var factory: UsersViewModelFactory

    lateinit var usersViewModel: UsersViewModel

    override val mTAG: String?
        get() = UsersPagedFragment::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        usersViewModel = ViewModelProviders.of(this, factory).get(UsersViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSwipeToRefresh()
        setUpRecyclerView()

        usersViewModel.users.observe(this,
            Observer<PagedList<User>> { listing ->
                if (listing != null && !listing.isEmpty()) {
                    showToast("users: " + listing.get(0))
                } else {
                    showToast("users: empty")
                }

                adapter.submitList(listing)
            })

        usersViewModel.networkState.observe(this, Observer {
            adapter.setNetworkState(it)
        })

        usersViewModel.loadUsers()
    }

    private fun initSwipeToRefresh() {
        usersViewModel.refreshState.observe(this, Observer { it ->
            swipe_refresh.isRefreshing = it == LOADING
        })
        swipe_refresh.setOnRefreshListener {
            usersViewModel.refresh()
        }
    }
}