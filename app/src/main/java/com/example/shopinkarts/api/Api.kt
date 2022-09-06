package com.example.shopinkarts.api

import com.example.shopinkarts.response.*
import retrofit2.Call
import retrofit2.http.*

interface Api {

    // user signup api
    @POST("users/signup")
    fun signup(@Body body: Map<String, String>): Call<SignUpResponse>

    // user login api
    @POST("users/login")
    fun login(@Body body: Map<String, String>): Call<LoginResponse>

    //dash board api
    @GET("dashboard")
    fun dashBoard(): Call<DashBoardResponse>

    // categories api
    @GET("categories")
    fun categories(): Call<CategoriesResponse>

    // sub categories api
    @GET("sub-categories/category/{id}")
    fun subCategories(@Path("id") id: String?): Call<SubCategoriesResponse>

    // particular item api
    @GET("products/sub-category/{id}?")
    fun particularItem(
        @Path("id") id: String?,
        @Query("type") type: String?,
        @Query("subType") subType: String?,
        @Query("value") value: String
    ): Call<ParticularItemResponse>
}