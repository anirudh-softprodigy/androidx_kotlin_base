package com.kuteapp.module.login

import com.android.kuteapp.api.RestService
import com.kuteapp.utils.PreferenceManager
import com.kuteapp.utils.ProgressBarHandler
import dagger.Module
import dagger.Provides

/**
 * Created by Anirudh_Sharma on 02-May-18.
 */
@Module
class LoginModule(private var loginActivity: LoginActivity) {
    @Provides
    fun getPresenter(restService: RestService, preferenceManager: PreferenceManager): LoginPresenter = LoginPresenter(restService, preferenceManager, loginActivity)

    @Provides
    fun getProgressBar(): ProgressBarHandler {
        return ProgressBarHandler(loginActivity)
    }
}