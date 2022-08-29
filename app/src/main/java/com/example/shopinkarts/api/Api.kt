package com.example.shopinkarts.api

import com.example.shopinkarts.response.DashBoardResponse
import com.example.shopinkarts.response.LoginResponse
import com.example.shopinkarts.response.SignUpResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

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

    //dash board api
    @GET("dashboard")
    fun dashBoard(
        @Query("useId") userId: String
    ): Call<DashBoardResponse>


}