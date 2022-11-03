package com.example.shopinkarts.api

import com.example.shopinkarts.model.CreateOrderRequest
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

    // product api
    @POST("products/details")
    fun productApi(@Body body: Map<String, String>): Call<ProductResponse>

    // order api
    @POST("orders/create")
    fun ordersApi(@Body body: CreateOrderRequest): Call<SuccessResponse>

    // my orders api
    @GET("orders/user?")
    fun myOrdersApi(@Query("id") id: String?): Call<MyOrdersResponse>

    // customer support api
    @POST("customer-supports/create")
    fun customerSupportApi(@Body body: Map<String, String>): Call<CustomerSupportResponse>

    // search api
    @GET("products/search?")
    fun search(@Query("searchId") searchId: String?): Call<SearchResponse>

    // notification api
    @GET("notifications/users/{id}")
    fun notification(@Path("id") id: String?): Call<NotificationResponse>

    // app setting api
    @GET("app-settings")
    fun appSetting(): Call<AppSettingResponse>

    // track order api
    @GET("orders/track?")
    fun trackOrder(@Query("id") id: String?): Call<TrackOrderResponse>

    //  forgot password send otp
    @POST("users/forgot-password/send-otp")
    fun forgotPasswordSendOtp(@Body body: Map<String, String>): Call<ForgotPasswordSendOtpResponse>
}