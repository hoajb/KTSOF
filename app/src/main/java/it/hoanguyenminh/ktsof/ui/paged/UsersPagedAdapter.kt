package it.hoanguyenminh.ktsof.ui.paged

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import it.hoanguyenminh.ktsof.base.recyclerview.BasePagedListAdapter
import it.hoanguyenminh.ktsof.base.recyclerview.ItemClickListener
import it.hoanguyenminh.ktsof.repository.data.User
import it.hoanguyenminh.ktsof.ui.main.UsersViewHolder

/**
 * Created by Hoa Nguyen on 2018 November 13.
 * hoa.nguyenminh.it@gmail.com
 */

class UsersPagedAdapter(private val retryCallback: () -> Unit, private val clickListener: ItemClickListener<User>?) :
    BasePagedListAdapter<User, UsersViewHolder>(diffCallback, retryCallback, clickListener) {

    override fun createItemViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder =
        UsersViewHolder.createInstance(parent, viewType)

    companion object {
        /**
         * This diff callback informs the PagedListAdapter how to compute list differences when new
         * PagedLists arrive.
         * <p>
         * When you add a Cheese with the 'Add' button, the PagedListAdapter uses diffCallback to
         * detect there's only a single item difference from before, so it only needs to animate and
         * rebind a single view.
         *
         * @see android.support.v7.util.DiffUtil
         */
        private val diffCallback = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean =
                oldItem.account_id == newItem.account_id

            /**
             * Note that in kotlin, == checking on data classes compares all contents, but in Java,
             * typically you'll implement Object#equals, and use it to compare object contents.
             */
            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean =
                oldItem == newItem
        }
    }
}