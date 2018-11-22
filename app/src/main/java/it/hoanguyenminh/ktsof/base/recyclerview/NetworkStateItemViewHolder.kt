package it.hoanguyenminh.ktsof.base.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import it.hoanguyenminh.ktsof.R
import it.hoanguyenminh.ktsof.repository.NetworkState
import it.hoanguyenminh.ktsof.repository.Status

/**
 * Created by Hoa Nguyen on 2018 November 20.
 * hoa.nguyenminh.it@gmail.com
 */

class NetworkStateItemViewHolder(
    view: View,
    private val retryCallback: () -> Unit
) : BaseViewHolder<NetworkState>(view) {
    override fun bindingData(data: NetworkState?) {
        progressBar.visibility = toVisibility(data?.status == Status.RUNNING)
        retry.visibility = toVisibility(data?.status == Status.FAILED)
        errorMsg.visibility = toVisibility(data?.msg != null)
        errorMsg.text = data?.msg
    }

    private val progressBar = view.findViewById<ProgressBar>(R.id.progress_bar)
    private val retry = view.findViewById<Button>(R.id.retry_button)
    private val errorMsg = view.findViewById<TextView>(R.id.error_msg)

    init {
        retry.setOnClickListener {
            retryCallback()
        }
    }

    companion object {
        fun create(parent: ViewGroup, retryCallback: () -> Unit): NetworkStateItemViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.network_state_item, parent, false)
            return NetworkStateItemViewHolder(view, retryCallback)
        }

        fun toVisibility(constraint: Boolean): Int {
            return if (constraint) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
    }

}