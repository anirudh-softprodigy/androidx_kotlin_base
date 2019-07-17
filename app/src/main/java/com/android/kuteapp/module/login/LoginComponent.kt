package com.kuteapp.module.login

import dagger.Subcomponent

/**
 * Created by Anirudh_Sharma on 02-May-18.
 */

@LoginScope
@Subcomponent(modules = [(LoginModule::class)])
interface LoginComponent {
    fun inject(loginActivity: LoginActivity)
}