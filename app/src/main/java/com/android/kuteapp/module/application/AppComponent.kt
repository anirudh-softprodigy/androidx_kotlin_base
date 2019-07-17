package com.kuteapp.module.application

import com.android.kuteapp.api.NetModule
import com.kuteapp.module.login.LoginComponent
import com.kuteapp.module.login.LoginModule
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Anirudh_Sharma on 11-Jun-18.
 */

@Singleton
@Component(modules = [(AppModule::class), (NetModule::class)])
interface AppComponent {

    fun plus(loginModule: LoginModule): LoginComponent

}