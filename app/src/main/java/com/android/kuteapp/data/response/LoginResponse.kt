package com.kuteapp.data.response

/**
 * Created by Anirudh_Sharma on 26-Jun-18.
 */


data class LoginResponse(
        val status: String = "",
        val status_code: Int = 0,
        val message: String = "",
        val data: Data = Data()
)

data class Data(
        val id: Int = 0,
        val first_name: String = "",
        val last_name: String = "",
        val username: String = "",
        val email: String = "",
        val profile_image: String = "",
        val gender: String = "",
        val date_of_birth: String = "",
        val access_token: String = "",
        val country_code: String = "",
        val phone: String = "",
        val role_id: Int = 0,
        val status: Int = 0,
        val deact_status: Int = 0,
        val otp_verified: Int = 0,
        val email_verified: Int = 0,
        val login_status: Int = 0,
        val created: String = "",
        val modified: String = "",
        val cover_image: String = ""
)