package it.hoanguyenminh.ktsof.ui.main

import android.view.ViewGroup
import it.hoanguyenminh.ktsof.base.recyclerview.BaseRecycleViewAdapter
import it.hoanguyenminh.ktsof.base.recyclerview.BaseViewHolder
import it.hoanguyenminh.ktsof.base.recyclerview.ItemClickListener
import it.hoanguyenminh.ktsof.repository.data.User

/**
 * Created by Hoa Nguyen on 2018 November 13.
 * hoa.nguyenminh.it@gmail.com
 */

class UsersAdapter(mListData: List<User>, private val clickListener: ItemClickListener<User>?) :
    BaseRecycleViewAdapter<User>(mListData, clickListener) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<User> {
        val holder = UsersViewHolder.createInstance(parent, viewType)
        holder.clickListener = clickListener
        return holder
    }
}