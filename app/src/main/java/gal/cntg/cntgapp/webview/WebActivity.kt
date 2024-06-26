package gal.cntg.cntgapp.webview

import android.os.Bundle
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import gal.cntg.cntgapp.R

class WebActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        val urlRemota:String = "https://github.com/PabloSanchez87/AndroidCNTG"

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_activity)

        var webView = findViewById<WebView>(R.id.webView)
        webView.loadUrl(urlRemota)                  // Pasamos url
        webView.settings.javaScriptEnabled = true   // Habilitamos JS, viene desactivado por defecto.
    }
}