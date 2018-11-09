package it.hoanguyenminh.ktsof.view.main

import android.os.Bundle
import dagger.android.DaggerActivity
import it.hoanguyenminh.ktsof.R

class MainActivity : DaggerActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
