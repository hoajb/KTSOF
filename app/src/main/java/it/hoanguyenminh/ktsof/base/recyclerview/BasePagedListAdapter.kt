package it.hoanguyenminh.ktsof.base.recyclerview

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import it.hoanguyenminh.ktsof.repository.NetworkState

/**
 * Created by Hoa Nguyen on 2018 November 20.
 * hoa.nguyenminh.it@gmail.com
 */

abstract class BasePagedListAdapter<T : Any, VH : BaseViewHolder<T>>(
    diffCallback: DiffUtil.ItemCallback<T>,
    private val retryCallback: () -> Unit,
    private var clickListener: ItemClickListener<T>? = null
) : PagedListAdapter<T, VH>(diffCallback) {

    companion object {
        const val TYPE_ITEM: Int = 0
        const val TYPE_NETWORK_STATE: Int = 1
    }

    abstract fun createItemViewHolder(parent: ViewGroup, viewType: Int): VH

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return if (viewType == TYPE_NETWORK_STATE) {
            @Suppress("UNCHECKED_CAST")
            NetworkStateItemViewHolder.create(parent, retryCallback) as VH
        } else {
            createItemViewHolder(parent, viewType)
        }
    }


    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.clickListener = clickListener
        holder.setData(getItem(position))
        when (getItemViewType(position)) {
            TYPE_ITEM -> holder.bindingData(getItem(position))
            TYPE_NETWORK_STATE -> (holder as NetworkStateItemViewHolder).bindingData(networkState)
        }
    }

    private var networkState: NetworkState? = null

    private fun hasExtraRow() = networkState != null && networkState != NetworkState.LOADED

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            TYPE_NETWORK_STATE
        } else {
            TYPE_ITEM
        }
    }

    fun setNetworkState(newNetworkState: NetworkState?) {
        val previousState = this.networkState
        val hadExtraRow = hasExtraRow()
        this.networkState = newNetworkState
        val hasExtraRow = hasExtraRow()
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(super.getItemCount())
            } else {
                notifyItemInserted(super.getItemCount())
            }
        } else if (hasExtraRow && previousState != newNetworkState) {
            notifyItemChanged(itemCount - 1)
        }
    }


}