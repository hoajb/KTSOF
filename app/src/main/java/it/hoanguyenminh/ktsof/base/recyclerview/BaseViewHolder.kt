package it.hoanguyenminh.ktsof.base.recyclerview

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import it.hoanguyenminh.ktsof.R
import it.hoanguyenminh.ktsof.config.GlideApp

/**
 * Created by Hoa Nguyen on 2018 November 11.
 * hoa.nguyenminh.it@gmail.com
 */

abstract class BaseViewHolder<T : Any>(itemView: View, itemClickListener: ItemClickListener<T>? = null) :
    RecyclerView.ViewHolder(itemView) {

    var clickListener: ItemClickListener<T>? = itemClickListener

    private lateinit var mData: T

    abstract fun bindingData(data: T)

    fun setData(data: T) {
        mData = data
    }

    init {
        itemView.setOnClickListener { getDefaultClickListener() }
    }

    open fun getDefaultClickListener(): View.OnClickListener {
        return View.OnClickListener { clickListener?.onItemClick(mData, adapterPosition, 0) }
    }

    fun showImage(imageView: ImageView, url: String) {
        GlideApp.with(itemView.context)
            .load(url)
            .placeholder(R.mipmap.ic_launcher)
            .fitCenter()
            .into(imageView)
    }
}

interface ItemClickListener<T> {
    fun onItemClick(data: T, position: Int, typeClick: Int = 0)
}

