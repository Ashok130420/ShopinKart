package com.example.shopinkarts.api

import com.example.shopinkarts.response.LoginResponse
import com.example.shopinkarts.response.SignUpResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface Api {

    // user signup api
    @POST("users/signup")
    fun signup(
        @Body body: Map<String, String>
    ): Call<SignUpResponse>

    // user login api
    @POST("users/login")
    fun login(
        @Body body: Map<String, String>
    ): Call<LoginResponse>

}