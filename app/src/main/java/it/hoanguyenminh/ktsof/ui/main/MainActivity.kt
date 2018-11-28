package it.hoanguyenminh.ktsof.ui.main

import android.os.Bundle
import com.google.gson.Gson
import it.hoanguyenminh.ktsof.application.SOFApplication
import it.hoanguyenminh.ktsof.base.view.BaseFragment
import it.hoanguyenminh.ktsof.base.view.BaseSingleFragmentActivity
import it.hoanguyenminh.ktsof.repository.data.ModelTest
import it.hoanguyenminh.ktsof.ui.paged.UsersPagedFragment
import timber.log.Timber
import javax.inject.Inject

class MainActivity : BaseSingleFragmentActivity() {

    @Inject
    lateinit var application: SOFApplication

    @Inject
    lateinit var gson: Gson

    //    override fun createFragment(): BaseFragment = UsersFragment()
    override fun createFragment(): BaseFragment = UsersPagedFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var model = ModelTest()
        model.name = "Nguyen Minh Hoa"
        model.account_id = "999"
        model.user_id = 100
        Timber.d("ModelTest :\n" + model.toString())

        var json = gson.toJson(model)

        Timber.d("ModelTest-Json: \n" + json.toString())

        var model2 = gson.fromJson(json, ModelTest::class.java)

        Timber.d("ModelTest2 : \n" + model2.toString())
    }


}
