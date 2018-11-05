package it.hoanguyenminh.ktsof.view

import android.widget.Toast
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import timber.log.Timber

open class BaseFragment : androidx.fragment.app.Fragment() {
    val subcriptions = CompositeDisposable()

    fun subcribe(disposable: Disposable): Disposable {
        subcriptions.add(disposable)
        return disposable
    }

    override fun onStop() {
        super.onStop()

        subcriptions.clear()
    }

    fun showMessage(message: String) {
        //show Dialog message
        Timber.d(message)
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun showLoading(message: String) {
//        val dialog = ProgressDialog(this.activity)
//        dialog.setMessage("Please wait")
//        dialog.setTitle("Loading")
//        dialog.setCancelable(false)
//        dialog.isIndeterminate = true
//        dialog.show()

//        alert {
//            customView {
//                editText()
//            }
//        }.show()
//
//        toast("Hi there!")
    }

    fun hideLoading() {

    }
}