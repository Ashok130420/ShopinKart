package com.example.shopinkarts.classes

import android.content.Context
import android.content.SharedPreferences

class SharedPreference(context: Context) {

    var prefs: SharedPreferences =
        context.getSharedPreferences("prefs", Context.MODE_PRIVATE)

    companion object {
        const val TOKEN = "token"
        const val userId = "userId"
        const val isLogin = "isLogin"
        const val usertype = "userType"
    }

    fun clear() {
        val editor: SharedPreferences.Editor = prefs.edit()
        editor.clear()
        editor.commit()
    }

    // For Token
    fun setToken(token: String) {
        val editor = prefs.edit()
        editor.putString(TOKEN, token)
        editor.apply()
    }

    fun getToken(): String? {
        return prefs.getString(TOKEN, " ")
    }

    fun setUserId(token: String) {
        val editor = prefs.edit()
        editor.putString(userId, token)
        editor.apply()
    }

    fun getUserId(): String? {
        return prefs.getString(userId, " ")
    }

    fun isLoginSet(value: Boolean) {
        val editor = prefs.edit()
        editor.putBoolean(isLogin, value)
        editor.apply()
    }

    fun isLoginGet(): Boolean {
        return prefs.getBoolean(isLogin, false)
    }
}