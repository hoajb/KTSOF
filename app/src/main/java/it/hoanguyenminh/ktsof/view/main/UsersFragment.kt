package it.hoanguyenminh.ktsof.view.main

import com.google.gson.Gson
import it.hoanguyenminh.ktsof.base.recyclerview.BaseRecycleViewAdapter
import it.hoanguyenminh.ktsof.base.recyclerview.ItemClickListener
import it.hoanguyenminh.ktsof.base.view.BaseListFragment
import it.hoanguyenminh.ktsof.repository.data.User
import javax.inject.Inject

/**
 * Created by Hoa Nguyen on 2018 November 13.
 * hoa.nguyenminh.it@gmail.com
 */

class UsersFragment : BaseListFragment<User>() {
    @Inject
    lateinit var mainActivity: MainActivity

    @Inject
    lateinit var gson: Gson

    override fun createAdapter(
        list: ArrayList<User>,
        itemClickListener: ItemClickListener<User>?
    ): BaseRecycleViewAdapter<User> = UsersAdapter(mListData = list, clickListener = itemClickListener)


    override val mTAG: String?
        get() = UsersFragment::class.simpleName

}