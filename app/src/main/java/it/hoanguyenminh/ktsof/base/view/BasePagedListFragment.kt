package it.hoanguyenminh.ktsof.base.view

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import it.hoanguyenminh.ktsof.R
import it.hoanguyenminh.ktsof.base.recyclerview.BasePagedListAdapter
import it.hoanguyenminh.ktsof.base.recyclerview.BaseViewHolder
import kotlinx.android.synthetic.main.base_fragment_single_list.*

abstract class BasePagedListFragment<T : Any, VH : BaseViewHolder<T>> : BaseFragment() {
    override val layoutId: Int
        get() = R.layout.base_fragment_single_list

    abstract fun createAdapter(
    ): BasePagedListAdapter<T, VH>

    lateinit var adapter: BasePagedListAdapter<T, VH>

    open fun getLayoutManager(): RecyclerView.LayoutManager =
        LinearLayoutManager(context, RecyclerView.VERTICAL, false)

    open fun setUpRecyclerView() {
        adapter = createAdapter()
        recyclerView.layoutManager = getLayoutManager()
        recyclerView.adapter = adapter
    }
}

