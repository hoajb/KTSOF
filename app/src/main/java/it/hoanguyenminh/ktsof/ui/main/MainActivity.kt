package it.hoanguyenminh.ktsof.ui.main

import android.os.Bundle
import it.hoanguyenminh.ktsof.application.SOFApplication
import it.hoanguyenminh.ktsof.base.view.BaseFragment
import it.hoanguyenminh.ktsof.base.view.BaseSingleFragmentActivity
import timber.log.Timber
import javax.inject.Inject

class MainActivity : BaseSingleFragmentActivity() {

    @Inject
    lateinit var application: SOFApplication

    override fun createFragment(): BaseFragment = UsersFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Timber.d("Application" + application.toString())
    }


}
