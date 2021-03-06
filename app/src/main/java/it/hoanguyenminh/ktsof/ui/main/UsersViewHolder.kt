package it.hoanguyenminh.ktsof.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import it.hoanguyenminh.ktsof.R
import it.hoanguyenminh.ktsof.base.recyclerview.BaseViewHolder
import it.hoanguyenminh.ktsof.base.recyclerview.ItemClickListener
import it.hoanguyenminh.ktsof.repository.data.User
import kotlinx.android.synthetic.main.item_user.view.*

/**
 * Created by Hoa Nguyen on 2018 November 12.
 * hoa.nguyenminh.it@gmail.com
 */

class UsersViewHolder(itemView: View, clickListener: ItemClickListener<User>? = null) :
    BaseViewHolder<User>(itemView, clickListener) {


    override fun bindingData(data: User?) {
        itemView.tvNo.text = adapterPosition.toString()
        itemView.tvname.text = data?.display_name
        itemView.tvlink.text = data?.link
        showImage(itemView.imAvatar, data?.profile_image ?: "")
    }

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