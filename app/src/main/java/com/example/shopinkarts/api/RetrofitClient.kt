package com.example.shopinkarts.api

import android.util.Log
import com.example.shopinkarts.classes.SharedPreference
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitClient private constructor() {
    var sharedPreferences: SharedPreference = SharedPreference(MyApplication.instance)

    //var interceptor = TokenInterceptor()
    var interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    var client: OkHttpClient =
        OkHttpClient.Builder().addInterceptor(interceptor).addInterceptor { chain ->

                val original: Request = chain.request()
                val requestBuilder: Request.Builder =
                    original.newBuilder().addHeader("Accept", "application/json")
                        .addHeader("Content-Type", "application/json")

                // Adding Authorization token (API Key)
                // Requests will be denied without API key

                Log.d("TAG", "header--------------: Bearer ${sharedPreferences.getToken()}")
            requestBuilder.addHeader("Authorization", "Bearer ${sharedPreferences.getToken()}")
                val request: Request = requestBuilder.build()
                chain.proceed(request)
            }.build()

    private val retrofit: Retrofit = Retrofit.Builder().client(client).baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create()).build()
    val api: Api
        get() = retrofit.create(Api::class.java)

    companion object {
        private const val BASE_URL = "https://shopinkartapp.herokuapp.com/api/"
        private var mInstance: RetrofitClient? = null


        @get:Synchronized
        val instance: RetrofitClient?
            get() {
                if (mInstance == null) {
                    mInstance = RetrofitClient()
                }
                return mInstance
            }
    }

}

