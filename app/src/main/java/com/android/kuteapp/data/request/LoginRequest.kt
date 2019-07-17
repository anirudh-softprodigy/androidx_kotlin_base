package com.kuteapp.data.request

/**
 * Created by Anirudh_Sharma on 26-Jun-18.
 */

data class LoginRequest(
        var email: String? = null,
        var password: String? = null,
        var first_name: String? = null,
        var last_name: String? = null,
        var username: String? = null,
        var user_fb_id: String? = null,
        var user_twitter_id: String? = null,
        var user_gmail_id: String? = null
)