package com.kuteapp.mvp

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kuteapp.utils.Extensions.getErrorMessage

/**
 * Created by Anirudh_Sharma on 01-May-18.
 */
 abstract class BaseMvpActivity
    : AppCompatActivity(), BaseMvpView {

    override fun showError(throwable: Throwable) {
        Toast.makeText(this, throwable.getErrorMessage(this), Toast.LENGTH_SHORT).show()
    }

    override fun showError(stringResId: Int) {
        Toast.makeText(this, stringResId, Toast.LENGTH_SHORT).show()
    }

    override fun showError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }

    override fun showMessage(srtResId: Int) {
        Toast.makeText(this, srtResId, Toast.LENGTH_SHORT).show()
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}