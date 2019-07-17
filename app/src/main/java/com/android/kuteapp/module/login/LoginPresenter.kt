package com.kuteapp.module.login

import android.text.TextUtils
import com.android.kuteapp.R
import com.android.kuteapp.api.RestService
import com.android.kuteapp.constants.ApiConstants
import com.kuteapp.data.request.LoginRequest
import com.kuteapp.mvp.BaseMvpPresenterImpl
import com.kuteapp.utils.PreferenceManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


/**
 * Created by Anirudh_Sharma on 06-Dec-17.
 * Presenter class for {@link ForgotPasswordActivity}
 */
class LoginPresenter(
    private val mRestService: RestService, private val preferenceManager: PreferenceManager,
    private val loginActivity: LoginActivity
) :
    BaseMvpPresenterImpl<LoginContract.View>(), LoginContract.Presenter {

    private var disposable: Disposable? = null

    /**
     * check if fields entered by user are valid
     */
    override fun validateFields(email: String, password: String) {
        when {
            TextUtils.isEmpty(email.trim()) -> mView?.showError(R.string.email_req)
            TextUtils.isEmpty(password.trim()) -> mView?.showError(R.string.pass_req)
            else -> {
                val loginRequest = LoginRequest()
                loginRequest.email = email
                loginRequest.password = password
                login(loginRequest)
            }
        }
    }

    /**
     * Api implementation to LOGIN users
     */
    private fun login(loginRequest: LoginRequest) {

        mView?.showProgress()
        disposable = mRestService.login(loginRequest)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                mView?.hideProgress()
                when {
                    it.status_code == ApiConstants.SUCCESS -> {
                        mView?.loginSuccessful()
                        preferenceManager.saveUserData(it.data)
                        preferenceManager.setUserLoggedIn(true)
                    }
                    it.status_code == ApiConstants.VERIFICATION_ERROR -> {
                        //do something
                    }
                    else -> mView?.showError(it.message)
                }
            }, {
                mView?.hideProgress()
                mView?.showError(it)
            })
    }


    override fun unRegister() {
        disposable?.dispose()
    }
}