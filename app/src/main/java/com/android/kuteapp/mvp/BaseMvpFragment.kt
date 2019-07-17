package com.kuteapp.mvp

import android.widget.Toast
import androidx.fragment.app.Fragment
import com.kuteapp.utils.Extensions.getErrorMessage

/**
 * Created by Anirudh_Sharma on 01-May-18.
 */
 abstract class BaseMvpFragment
    : Fragment(), BaseMvpView {

    override fun showError(throwable: Throwable) {
        Toast.makeText(context, throwable.getErrorMessage(context!!), Toast.LENGTH_SHORT).show()
    }

    override fun showError(stringResId: Int) {
        Toast.makeText(context, stringResId, Toast.LENGTH_SHORT).show()
    }

    override fun showError(error: String) {
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
    }

    override fun showMessage(srtResId: Int) {
        Toast.makeText(context, srtResId, Toast.LENGTH_SHORT).show()
    }

    override fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

}