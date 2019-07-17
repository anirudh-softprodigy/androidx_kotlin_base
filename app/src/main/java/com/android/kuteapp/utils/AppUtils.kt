package com.kuteapp.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings

/**
 * Created by Anirudh_Sharma on 13-Jun-18.
 */
class AppUtils(private val mContext: Context){
    /**
     * redirect user to your application settings in device
     */

    fun redirectToAppSettings() {
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        val uri = Uri.fromParts("package", mContext.getPackageName(), null)
        intent.data = uri
        mContext.startActivity(intent)
    }
}