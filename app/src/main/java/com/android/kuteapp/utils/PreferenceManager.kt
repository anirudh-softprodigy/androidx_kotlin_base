package com.kuteapp.utils

import android.content.Context
import android.content.SharedPreferences
import com.android.kuteapp.R
import com.kuteapp.data.response.Data
import com.squareup.moshi.Moshi


/**
 * Created by Anirudh_Sharma on 07-Jun-18.
 */
class PreferenceManager(context: Context) {

    private val USER_PROFILE = "userProfile"
    private val LOGGED_IN = "userLogged"

    private var mContext: Context? = context


    //get shared pref
    private fun getPreferences(): SharedPreferences? {
        return mContext!!.getSharedPreferences(mContext!!.getString(R.string.app_name), Context.MODE_PRIVATE)
    }


    fun setStringValues(key: String, DataToSave: String) {
        val editor = getPreferences()!!.edit()
        editor.putString(key, DataToSave)
        editor.apply()

    }

    fun getStringValues(key: String): String? {
        return getPreferences()!!.getString(key, null)
    }

    fun setIntValues(key: String, DataToSave: Int) {
        val editor = getPreferences()!!.edit()
        editor.putInt(key, DataToSave)
        editor.apply()

    }

    fun getIntValues(key: String): Int {
        return getPreferences()!!.getInt(key, 0)
    }

    fun setUserLoggedIn(isLoggedIn: Boolean) {
        val editor = getPreferences()!!.edit()
        editor.putBoolean(LOGGED_IN, isLoggedIn)
        editor.apply()
    }

    fun isUserLoggedIn(): Boolean {
        return getPreferences()!!.getBoolean(LOGGED_IN, false)
    }

    //save user data
    fun saveUserData(response: Data?) {
        val moshi = Moshi.Builder().build()
        val jsonAdapter = moshi.adapter<Data>(Data::class.java)
        val json = jsonAdapter.toJson(response)
        setStringValues(USER_PROFILE, json)
    }

    //get user data
    fun getUserData(): Data {
        val moshi = Moshi.Builder().build()
        val jsonAdapter = moshi.adapter<Data>(Data::class.java)
        return jsonAdapter.fromJson(getStringValues(USER_PROFILE))
    }

    //clear user shared preferences
    fun clearPreferences() {
        getPreferences()!!.edit().clear().apply()
    }

}