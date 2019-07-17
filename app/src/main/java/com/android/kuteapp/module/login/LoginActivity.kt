package com.kuteapp.module.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.android.kuteapp.R
import com.android.kuteapp.module.MainActivity
import com.kuteapp.module.application.SampleApplication
import com.kuteapp.mvp.BaseMvpActivity
import com.kuteapp.utils.ProgressBarHandler
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

class LoginActivity : BaseMvpActivity(), LoginContract.View, View.OnClickListener {

    @Inject
    lateinit var mPresenter: LoginPresenter
    @Inject
    lateinit var mProgressBarHandler: ProgressBarHandler


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        (application as SampleApplication).createLoginComponent(this)?.inject(this)

        mPresenter.attachView(this)

        btnSubmit.setOnClickListener(this)
        txtForgotPassword.setOnClickListener(this)

    }

    /**
     * user login successful
     */
    override fun loginSuccessful() {
        finish()
    }

    override fun showProgress() {
        mProgressBarHandler.showProgress()
    }

    override fun hideProgress() {
        mProgressBarHandler.hideProgress()
    }

    /**
     * click handle on views
     */
    override fun onClick(p0: View?) {
        when (p0) {
            btnSubmit -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                //   mPresenter.validateFields(edtEmailAddress.text.toString(), edtPassword.text.toString())
            }

            txtForgotPassword -> finish()
        }
    }

    /**
     * release all listeners and components
     */
    override fun onDestroy() {
        super.onDestroy()
        (application as SampleApplication).releaseLoginComponent()
        mPresenter.detachView()
        mPresenter.unRegister()
    }
}
