package com.example.shopinkarts.activity


import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.example.shopinkarts.R


class PdfActivity : AppCompatActivity() {

    var pdfUrl = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdf)

        pdfUrl = intent.extras!!.getString("pdfLink", "")

        val webView = findViewById<WebView>(R.id.webView)
        webView.webViewClient = WebViewClient()
        webView.settings.setSupportZoom(true)
        webView.settings.javaScriptEnabled = true

        webView.loadUrl("https://docs.google.com/gview?embedded=true&url=" + pdfUrl)

    }
}