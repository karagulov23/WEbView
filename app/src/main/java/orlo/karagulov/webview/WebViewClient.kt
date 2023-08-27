import android.content.Intent
import android.net.Uri
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient

class CustomWebViewClient : WebViewClient() {
    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        val url = request?.url?.toString()
        if (url != null) {
            if (url != null && url.startsWith("http") || url.startsWith("https")) {
                return false // Позволить WebView обработать запрос
            }
        }
        // Если URL не начинается с "http" или "https", считаем, что это файл и обрабатываем загрузку
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        view?.context?.startActivity(intent)

        return true
    }
}
