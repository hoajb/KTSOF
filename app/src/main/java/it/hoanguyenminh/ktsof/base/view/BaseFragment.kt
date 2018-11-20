package it.hoanguyenminh.ktsof.base.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import dagger.android.support.DaggerFragment
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import it.hoanguyenminh.ktsof.custom.LoadingDialog
import timber.log.Timber

abstract class BaseFragment : DaggerFragment() {
    private lateinit var dialog: LoadingDialog

    abstract val layoutId: Int
    abstract val mTAG: String?

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutId, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog = LoadingDialog(view.context)
        dialog.setMessage("Loading")
        dialog.setCancelable(false)
    }

    private val subcriptions = CompositeDisposable()

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
        if (!dialog.isShowing)
            dialog.show()
    }

    fun hideLoading() {
        if (dialog.isShowing)
            dialog.dismiss()
    }

    fun showToast(mess: String) {
        Toast.makeText(context, mess, Toast.LENGTH_SHORT).show()
    }
}