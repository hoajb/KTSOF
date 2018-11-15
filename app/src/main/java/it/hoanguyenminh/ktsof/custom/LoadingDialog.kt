package it.hoanguyenminh.ktsof.custom

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import it.hoanguyenminh.ktsof.R
import kotlinx.android.synthetic.main.dialog_loading.*

class LoadingDialog(context: Context) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_loading)
    }

    fun setMessage(str: String): LoadingDialog {
        message.text = str
        return this
    }

}