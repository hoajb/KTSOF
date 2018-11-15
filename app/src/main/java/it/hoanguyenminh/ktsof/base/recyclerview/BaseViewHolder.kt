package it.hoanguyenminh.ktsof.base.recyclerview

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Hoa Nguyen on 2018 November 11.
 * hoa.nguyenminh.it@gmail.com
 */

abstract class BaseViewHolder<T : Any>(itemView: View, itemClickListener: ItemClickListener<T>? = null) :
    RecyclerView.ViewHolder(itemView) {

    var clickListener: ItemClickListener<T>? = itemClickListener

    private lateinit var mData: T

    open fun bindingData(data: T) {
        mData = data
    }

    init {
        itemView.setOnClickListener { getDefaultClickListener() }
    }

    open fun getDefaultClickListener(): View.OnClickListener {
        return View.OnClickListener { clickListener?.onItemClick(mData, adapterPosition, 0) }
    }
}

interface ItemClickListener<T> {
    fun onItemClick(data: T, position: Int, typeClick: Int = 0)
}

