package com.example.shopinkarts.classes

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.shopinkarts.activity.DashBoardActivity
import com.example.shopinkarts.model.CartModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class SharedPreference(val context: Context) {

    var prefs: SharedPreferences =
        context.getSharedPreferences("prefs", Context.MODE_PRIVATE)

    companion object {
        const val TOKEN = "token"
        const val userId = "userId"
        const val isLogin = "isLogin"
        const val userType = "userType"
    }

    fun clear() {
        val editor: SharedPreferences.Editor = prefs.edit()
        editor.clear()
        editor.commit()
    }

    fun setArray() {

//        val prefs: SharedPreferences = getSharedPreferences("prefs", Context.MODE_PRIVATE)

        val editor = prefs.edit()
        val gson = Gson()

        val json: String = gson.toJson(DashBoardActivity.arrayListCart)

        editor.putString("arrayListCart", json)

        editor.apply()

//      Toast.makeText(this, "Saved Array List to Shared preferences. ", Toast.LENGTH_SHORT).show()
    }

    fun getArray() {

        val gson = Gson()
        if (prefs.contains("arrayListCart")) {
            val json = prefs.getString("arrayListCart", null)

            val type: Type = object : TypeToken<ArrayList<CartModel?>?>() {}.type

            DashBoardActivity.arrayListCart =
                gson.fromJson<Any>(json, type) as ArrayList<CartModel>


        }
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

    fun setUsertype(usertype: String) {
        val editor = prefs.edit()
        editor.putString(userType, usertype)
        editor.apply()
    }

    fun getUserType(): String? {
        return prefs.getString(userType, "")

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