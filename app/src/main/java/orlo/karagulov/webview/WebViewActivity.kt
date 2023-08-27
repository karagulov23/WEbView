package orlo.karagulov.webview

import CustomWebViewClient
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.View
import android.webkit.CookieManager
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity

class WebViewActivity:AppCompatActivity() {

    private lateinit var webView: WebView
    private lateinit var currentUrl: String // Для сохранения текущего URL
    private var webViewState: Bundle? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)

        webView = findViewById(R.id.webView)
        webView.webViewClient = CustomWebViewClient()


        val url = intent.getStringExtra("url")

        val settings = webView.settings
        settings.javaScriptEnabled = true
        settings.domStorageEnabled = true
        settings.allowFileAccess = true

        val cookieManager = CookieManager.getInstance()
        cookieManager.setAcceptCookie(true)

        val sharedPreferences = getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
        val lastDomain = sharedPreferences.getString("lastDomain", url)

        if (lastDomain != null) {
            webView.loadUrl(lastDomain)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        webViewState = Bundle()
        webView.saveState(webViewState!!)
        outState.putBundle("webViewState", webViewState)
    }



    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack() // Вернуться на предыдущую страницу, если возможно
        } else {
//            finish()
            super.onBackPressed() // Если нельзя вернуться, выполнить обычное поведение кнопки "Назад"
        }
    }

    override fun onPause() {
        super.onPause()
        val lastDomain = webView.url
        val sharedPreferences = getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
        sharedPreferences.edit().putString("lastDomain", lastDomain).apply()
    }

}


