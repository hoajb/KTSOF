package it.hoanguyenminh.ktsof.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import it.hoanguyenminh.ktsof.R
import it.hoanguyenminh.ktsof.base.recyclerview.BaseViewHolder
import it.hoanguyenminh.ktsof.base.recyclerview.ItemClickListener
import it.hoanguyenminh.ktsof.repository.data.User

/**
 * Created by Hoa Nguyen on 2018 November 12.
 * hoa.nguyenminh.it@gmail.com
 */

class UsersViewHolder(itemView: View, clickListener: ItemClickListener<User>? = null) :
    BaseViewHolder<User>(itemView, clickListener) {

    override fun bindingData(data: User) {
        super.bindingData(data)


    }

    //    @Inject
//    lateinit var usersFragment: UsersFragment
//    @Inject
//    lateinit var mainActivity: MainActivity

    companion object {
        fun createInstance(parent: ViewGroup, viewType: Int = 0): UsersViewHolder {
            return UsersViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_user,
                    parent,
                    false
                )
            )
        }
    }


}