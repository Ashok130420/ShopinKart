package com.app.shopinkarts.classes

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.WindowManager
import androidx.core.content.ContextCompat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class Utils {

    companion object {

        fun statusBarOverride(activity: Activity) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                setWindowFlag(activity, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true)
            }
            if (Build.VERSION.SDK_INT >= 19) {
                activity.window.decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR!! or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            }
            //make fully Android Transparent Status bar
            if (Build.VERSION.SDK_INT >= 21) {
                setWindowFlag(activity, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
                activity.window.statusBarColor = Color.TRANSPARENT
            }
        }

        fun changeStatusColor(activity: Activity, resourceColor: Int) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                activity.window.statusBarColor =
                    ContextCompat.getColor(activity.applicationContext, resourceColor)
            }
        }

        fun changeStatusTextColor(activity: Activity) {
            activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        fun setWindowFlag(activity: Activity, bits: Int, on: Boolean) {
            val win = activity.window
            val winParams = win.attributes
            if (on) {
                winParams.flags = winParams.flags or bits
            } else {
                winParams.flags = winParams.flags and bits.inv()
            }
            win.attributes = winParams
        }


        @SuppressLint("SimpleDateFormat")
        @Throws(ParseException::class)
        fun formatDateFromDateString(
            inputDateFormat: String?,
            outputDateFormat: String?,
            inputDate: String?
        ): String? {
            val inputFormat = SimpleDateFormat(inputDateFormat, Locale.ENGLISH)
            val outputFormat = SimpleDateFormat(outputDateFormat, Locale.ENGLISH)
            val date = inputFormat.parse(inputDate)
            return outputFormat.format(date)
        }

        @SuppressLint("SimpleDateFormat")
        @Throws(ParseException::class)
        fun formatDateFromDateDate(
            inputDateFormat: String?,
            outputDateFormat: String?,
            inputDate: String?
        ): Date? {
            val inputFormat = SimpleDateFormat(inputDateFormat)
            val outputFormat = SimpleDateFormat(outputDateFormat)
            return inputFormat.parse(inputDate)
        }
    }
}