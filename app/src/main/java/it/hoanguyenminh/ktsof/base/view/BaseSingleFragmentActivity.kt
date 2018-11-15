package it.hoanguyenminh.ktsof.base.view

import android.os.Bundle
import it.hoanguyenminh.ktsof.R

/**
 * Created by Hoa Nguyen on 2018 November 09.
 * hoa.nguyenminh.it@gmail.com
 */
abstract class BaseSingleFragmentActivity : BaseActivity() {

    override val layoutId: Int
        get() = R.layout.activity_single_fragment

    abstract fun createFragment(): BaseFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val fragment = createFragment()
        supportFragmentManager.beginTransaction()
            .add(R.id.contentFrame, fragment, fragment.mTAG)
            .commit()

    }
}