package com.kuteapp.mvp

import androidx.annotation.StringRes


/**
 * Created by Anirudh_Sharma on 01-May-18.
 */
interface BaseMvpView {

    fun showError(throwable: Throwable)

    fun showError(@StringRes stringResId: Int)

    fun showError(error: String)

    fun showMessage(@StringRes srtResId: Int)

    fun showMessage(message: String)

}
