package it.hoanguyenminh.ktsof.view.main

import android.app.Application
import com.google.gson.Gson
import it.hoanguyenminh.ktsof.base.view.BaseFragment
import it.hoanguyenminh.ktsof.base.view.BaseSingleFragmentActivity
import javax.inject.Inject

class MainActivity : BaseSingleFragmentActivity() {
    @Inject
    lateinit var app: Application

    @Inject
    lateinit var gson: Gson

//    @Inject
//    lateinit var usersFragment: UsersFragment

    override fun createFragment(): BaseFragment = UsersFragment()

}
