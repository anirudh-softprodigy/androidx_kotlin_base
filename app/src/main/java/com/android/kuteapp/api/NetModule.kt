package com.android.kuteapp.api

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Anirudh_Sharma on 10-May-18.
 */
@Module
class NetModule {

    @Provides
    @Singleton
    fun getRestService() = ApiManager.create()
}