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

        const val Name = "name"
        const val Phone = "phone"
        const val Flat = "flat"
        const val Street = "street"
        const val Pin = "pin"
        const val City = "city"
        const val State = "state"
        const val Landmark = "landmark"

    }

    fun clear() {
        val editor: SharedPreferences.Editor = prefs.edit()
        editor.clear()
        editor.commit()
    }

    fun setAddress(
        name: String,
        phone: String,
        flat: String,
        street: String,
        pin: String,
        city: String,
        state: String,
        landmark: String
    ) {
        val editor = prefs.edit()
        editor.putString(Name, name)
        editor.putString(Phone, phone)
        editor.putString(Flat, flat)
        editor.putString(Street, street)
        editor.putString(Pin, pin)
        editor.putString(City, city)
        editor.putString(State, state)
        editor.putString(Landmark, landmark)

        editor.apply()
    }

    fun getName(): String? {
        return prefs.getString(Name, "")
    }

    fun getPhone(): String? {
        return prefs.getString(Phone, "")
    }

    fun getFlat(): String? {
        return prefs.getString(Flat, "")
    }

    fun getStreet(): String? {
        return prefs.getString(Street, "")
    }

    fun getPin(): String? {
        return prefs.getString(Pin, "")
    }

    fun getCity(): String? {
        return prefs.getString(City, "")
    }

    fun getState(): String? {
        return prefs.getString(State, "")
    }

    fun getLandmark(): String? {
        return prefs.getString(Landmark, "")
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

    fun setArray() {


        val editor = prefs.edit()
        val gson = Gson()

        val json: String = gson.toJson(DashBoardActivity.arrayListCart)
        val json2: String = gson.toJson(DashBoardActivity.selectedVIDs)

        editor.putString("arrayListCart", json)
        editor.putString("selectedVIDs", json2)

        editor.apply()

    }

    fun getArray() {

        val gson = Gson()
        if (prefs.contains("arrayListCart") && prefs.contains("selectedVIDs")) {

            val json = prefs.getString("arrayListCart", null)
            val json2 = prefs.getString("selectedVIDs", null)

            val type: Type = object : TypeToken<ArrayList<CartModel?>?>() {}.type
            val type1: Type = object : TypeToken<ArrayList<String?>?>() {}.type

            DashBoardActivity.arrayListCart =
                gson.fromJson<Any>(json, type) as ArrayList<CartModel>

            DashBoardActivity.selectedVIDs =
                gson.fromJson<Any>(json2, type1) as ArrayList<String>

        }

    }

}