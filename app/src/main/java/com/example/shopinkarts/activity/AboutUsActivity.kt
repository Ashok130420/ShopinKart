package com.example.shopinkarts.activity

import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.shopinkarts.R
import com.example.shopinkarts.classes.Utils
import com.example.shopinkarts.databinding.ActivityAboutUsBinding
import java.util.*

class AboutUsActivity : AppCompatActivity() {

    lateinit var binding: ActivityAboutUsBinding
    var pdfUrl = ""
    var header = ""
    var date = ""
    var dateFormat = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Utils.changeStatusTextColor(this)
        Utils.changeStatusColor(this,R.color.white)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_about_us)

        date = intent.extras!!.getString("date", "")
        Log.d("pdfUrl_date", "onCreate: $date")

//        getDate(date.toLong())
//        Log.d("pdfUrl_date", "onCreate: ${getDate(date.toLong())}")

        header = intent.extras!!.getString("header", "")
        binding.headerAbout.nameTV.text = header

        binding.headerAbout.backIV.setOnClickListener {
            onBackPressed()
        }

        pdfUrl = intent.extras!!.getString("pdfUrl", "")
        Log.d("pdfUrl", "onCreate: $pdfUrl")

        binding.webView.webViewClient = WebViewClient()
        binding.webView.settings.setSupportZoom(true)

        binding.webView.settings.javaScriptEnabled = true

        binding.webView.loadUrl("https://docs.google.com/gview?embedded=true&url=" + pdfUrl)
    }

    private fun getDate(time: Long): String? {
        val cal = Calendar.getInstance(Locale.ENGLISH)
        cal.timeInMillis = time * 1000
        return DateFormat.format("dd-MM-yyyy", cal).toString()
    }

}