package com.kuteapp.module.application

import android.app.Application
import android.content.Context
import com.android.kuteapp.R
import com.kuteapp.module.login.LoginActivity
import com.kuteapp.module.login.LoginComponent
import com.kuteapp.module.login.LoginModule
import io.github.inflationx.calligraphy3.CalligraphyConfig
import io.github.inflationx.calligraphy3.CalligraphyInterceptor
import io.github.inflationx.viewpump.ViewPump
import io.github.inflationx.viewpump.ViewPumpContextWrapper

/**
 * Created by Anirudh_Sharma on 07-Jun-18.
 */
class SampleApplication : Application() {

    private var loginComponent: LoginComponent? = null

    override fun onCreate() {
        super.onCreate()

        ViewPump.init(ViewPump.builder()
                .addInterceptor(CalligraphyInterceptor(
                        CalligraphyConfig.Builder()
                                .setDefaultFontPath("fonts/roboto_regular.ttf")
                                .setFontAttrId(R.attr.fontPath)
                                .build())).build())

    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase))
    }

    //create app co                               mponent
    private fun createAppComponent(): AppComponent {
        return DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }

    //create login component
    fun createLoginComponent(loginActivity: LoginActivity): LoginComponent? {
        loginComponent = createAppComponent().plus(LoginModule(loginActivity))
        return loginComponent
    }

    //release created login component
    fun releaseLoginComponent() {
        loginComponent = null
    }
}