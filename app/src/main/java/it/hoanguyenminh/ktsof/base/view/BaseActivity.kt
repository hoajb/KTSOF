package it.hoanguyenminh.ktsof.base.view

import android.os.Bundle
import dagger.android.support.DaggerAppCompatActivity
import it.hoanguyenminh.ktsof.custom.LoadingDialog

/**
 * Created by Hoa Nguyen on 2018 November 09.
 * hoa.nguyenminh.it@gmail.com
 */

abstract class BaseActivity : DaggerAppCompatActivity() {
    abstract val layoutId: Int

    private lateinit var loadingDialog: LoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)

        initView()
    }

    private fun initView() {
        loadingDialog = LoadingDialog(this)
    }

    fun showLoading() {
        if (!loadingDialog.isShowing)
            loadingDialog.show()
    }

    fun hideLoading() {
        if (loadingDialog.isShowing)
            loadingDialog.dismiss()
    }
}
