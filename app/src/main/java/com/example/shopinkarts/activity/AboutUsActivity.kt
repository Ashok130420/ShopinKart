package com.example.shopinkarts.activity

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.shopinkarts.R
import com.example.shopinkarts.databinding.ActivityAboutUsBinding
import com.github.barteksc.pdfviewer.PDFView
import java.io.BufferedInputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection


class AboutUsActivity : AppCompatActivity() {

    lateinit var binding: ActivityAboutUsBinding
    var pdfUrl = ""
    var header = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_about_us)

        header = intent.extras!!.getString("header", "")
        binding.headerAbout.nameTV.text = header

        binding.headerAbout.backIV.setOnClickListener {
            onBackPressed()
        }

        pdfUrl = intent.extras!!.getString("pdfUrl", "")
//        pdfUrl = "http://134.209.151.175/api/download/1661751944497sample.pdf"

        Log.d("pdfUrl", "onCreate: $pdfUrl")

        binding.webView.webViewClient = WebViewClient()
        binding.webView.settings.setSupportZoom(true)
        binding.webView.settings.javaScriptEnabled = true

        binding.webView.loadUrl("https://docs.google.com/gview?embedded=true&url=" + pdfUrl)
    }
}