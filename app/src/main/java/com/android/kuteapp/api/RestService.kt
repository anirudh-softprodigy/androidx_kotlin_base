package com.android.kuteapp.api

import com.android.kuteapp.constants.ApiConstants
import com.kuteapp.data.request.LoginRequest
import com.kuteapp.data.response.LoginResponse
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

interface RestService {

    //LOGIN USER API
    @POST(ApiConstants.LOGIN)
    fun login(@Body loginRequest: LoginRequest): Observable<LoginResponse>

}