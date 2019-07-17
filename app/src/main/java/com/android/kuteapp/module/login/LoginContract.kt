package com.kuteapp.module.login

import com.kuteapp.mvp.BaseMvpPresenter
import com.kuteapp.mvp.BaseMvpView

/**
 * Created by Anirudh_Sharma on 01-May-18.
 * Contract class for ForgotPasswordPresenter
 */
object LoginContract {

    interface View : BaseMvpView {
        fun loginSuccessful()

        fun showProgress()

        fun hideProgress()

    }

    interface Presenter : BaseMvpPresenter<View> {
        fun unRegister()
        fun validateFields(email: String, password: String)
    }
}