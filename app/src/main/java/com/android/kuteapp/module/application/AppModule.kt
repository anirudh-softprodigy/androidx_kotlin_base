package com.kuteapp.module.application

import android.content.Context
import com.kuteapp.utils.AppUtils
import com.kuteapp.utils.ImageUtils
import com.kuteapp.utils.PreferenceManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Anirudh_Sharma on 11-Jun-18.
 */
@Module
class AppModule(context: Context) {

    private val mContext: Context = context

    @Provides
    @Singleton
    fun getSharedPrefs(): PreferenceManager {
        return PreferenceManager(mContext)
    }

    @Provides
    @Singleton
    fun getImageUtils(): ImageUtils {
        return ImageUtils(mContext)
    }

    @Provides
    @Singleton
    fun getAPpUtils(): AppUtils {
        return AppUtils(mContext)
    }
}