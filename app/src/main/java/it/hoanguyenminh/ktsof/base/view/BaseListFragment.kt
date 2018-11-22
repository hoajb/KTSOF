package it.hoanguyenminh.ktsof.base.view

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import it.hoanguyenminh.ktsof.R
import it.hoanguyenminh.ktsof.base.recyclerview.BaseRecycleViewAdapter
import it.hoanguyenminh.ktsof.base.recyclerview.ItemClickListener
import kotlinx.android.synthetic.main.base_fragment_single_list.*

abstract class BaseListFragment<T : Any> : BaseFragment() {
    override val layoutId: Int
        get() = R.layout.base_fragment_single_list

    abstract fun createAdapter(
        list: List<T>,
        clickListener: ItemClickListener<T>? = null
    ): BaseRecycleViewAdapter<T>

    lateinit var adapter: BaseRecycleViewAdapter<T>

    open fun getItemClickListener(): ItemClickListener<T>? = null
    open fun getLayoutManager(): RecyclerView.LayoutManager =
        LinearLayoutManager(context, RecyclerView.VERTICAL, false)

    open fun setUpRecyclerView(list: List<T>) {
        if (!::adapter.isInitialized) {
            adapter = createAdapter(list, getItemClickListener())
            recyclerView.layoutManager = getLayoutManager()
            recyclerView.adapter = adapter
        } else {
            adapter.updateData(list)
            adapter.notifyDataSetChanged()
        }

        if (list.isEmpty()) {
        }
    }
}

